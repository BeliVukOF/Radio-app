package com.example.radioapp.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class StationManager(private val context: Context) {
    private val gson = Gson()
    private val client = OkHttpClient()
    private val cacheFile = File(context.filesDir, "stations_cache_v2.json")
    
    private val regions = listOf(
        "albania", "bosnia", "bulgaria", "croatia", 
        "greece", "macedonia", "montenegro", "serbia"
    )

    private fun getRemoteUrl(region: String) = 
        "https://raw.githubusercontent.com/BeliVukOF/Radio-app/main/stations/$region.json"

    suspend fun getStations(): List<RadioStation> = withContext(Dispatchers.IO) {
        val remoteStations = fetchAllRemoteStations()
        
        if (remoteStations.isNotEmpty()) {
            saveToCache(remoteStations)
            return@withContext remoteStations
        }

        val cachedStations = loadFromCache()
        if (cachedStations != null) {
            return@withContext cachedStations
        }

        return@withContext emptyList()
    }

    private suspend fun fetchAllRemoteStations(): List<RadioStation> = coroutineScope {
        regions.map { region ->
            async { fetchRegionStations(region) }
        }.awaitAll().flatten()
    }

    private fun fetchRegionStations(region: String): List<RadioStation> {
        return try {
            val url = getRemoteUrl(region)
            val request = Request.Builder().url(url).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return emptyList()
                val json = response.body?.string() ?: return emptyList()
                val type = object : TypeToken<List<RadioStation>>() {}.type
                gson.fromJson(json, type) ?: emptyList()
            }
        } catch (e: Exception) {
            Log.e("StationManager", "Failed to fetch $region stations", e)
            emptyList()
        }
    }

    private fun saveToCache(stations: List<RadioStation>) {
        try {
            val json = gson.toJson(stations)
            cacheFile.writeText(json)
        } catch (e: Exception) {
            Log.e("StationManager", "Failed to save stations to cache", e)
        }
    }

    private fun loadFromCache(): List<RadioStation>? {
        return try {
            if (!cacheFile.exists()) return null
            val json = cacheFile.readText()
            val type = object : TypeToken<List<RadioStation>>() {}.type
            gson.fromJson(json, type)
        } catch (e: Exception) {
            Log.e("StationManager", "Failed to load stations from cache", e)
            null
        }
    }
}
