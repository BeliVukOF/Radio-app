package com.example.radioapp.data

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File

class StationManager(private val context: Context) {
    private val gson = Gson()
    private val client = OkHttpClient()
    private val cacheFile = File(context.filesDir, "stations_cache.json")
    private val remoteUrl = "https://raw.githubusercontent.com/BeliVukOF/Radio-app/main/stations.json"

    suspend fun getStations(): List<RadioStation> = withContext(Dispatchers.IO) {
        // 1. Try to fetch from remote
        val remoteStations = fetchRemoteStations()
        if (remoteStations != null) {
            saveToCache(remoteStations)
            return@withContext remoteStations
        }

        // 2. Try to fetch from cache
        val cachedStations = loadFromCache()
        if (cachedStations != null) {
            return@withContext cachedStations
        }

        // 3. Fallback to hardcoded list if everything fails
        return@withContext emptyList()
    }

    private fun fetchRemoteStations(): List<RadioStation>? {
        return try {
            val request = Request.Builder().url(remoteUrl).build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return null
                val json = response.body?.string() ?: return null
                val type = object : TypeToken<List<RadioStation>>() {}.type
                gson.fromJson(json, type)
            }
        } catch (e: Exception) {
            Log.e("StationManager", "Failed to fetch remote stations", e)
            null
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
