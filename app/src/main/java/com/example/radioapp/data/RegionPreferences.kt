package com.example.radioapp.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class RegionPreferences(context: Context) {
    // Koristimo standardni SharedPreferences radi maksimalne kompatibilnosti sa Honor/Huawei uredjajima
    private val prefs: SharedPreferences = context.getSharedPreferences("region_prefs_v2", Context.MODE_PRIVATE)

    fun isRegionEnabled(region: String): Boolean = prefs.getBoolean(region, true)

    fun setRegionEnabled(region: String, enabled: Boolean) = prefs.edit { putBoolean(region, enabled) }

    var isPowerSavingEnabled: Boolean
        get() = prefs.getBoolean("power_saving", false)
        set(value) = prefs.edit { putBoolean("power_saving", value) }

    var appLanguage: String
        get() = prefs.getString("app_language", "en") ?: "en"
        set(value) = prefs.edit { putString("app_language", value) }

    fun isStationFavorite(streamUrl: String): Boolean {
        return prefs.getStringSet("favorites", emptySet())?.contains(streamUrl) == true
    }

    fun toggleFavorite(streamUrl: String) {
        val favorites = prefs.getStringSet("favorites", emptySet())?.toMutableSet() ?: mutableSetOf()
        if (favorites.contains(streamUrl)) favorites.remove(streamUrl) else favorites.add(streamUrl)
        prefs.edit { putStringSet("favorites", favorites) }
    }

    fun registerListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        prefs.unregisterOnSharedPreferenceChangeListener(listener)
    }
}
