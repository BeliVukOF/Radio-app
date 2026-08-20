package com.example.radioapp.service

import android.content.SharedPreferences
import androidx.media3.common.AudioAttributes
import androidx.media3.common.C
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.okhttp.OkHttpDataSource
import androidx.media3.exoplayer.DefaultLoadControl
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.session.MediaSession
import androidx.media3.session.MediaSessionService
import com.example.radioapp.data.RegionPreferences
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class PlaybackService : MediaSessionService(), SharedPreferences.OnSharedPreferenceChangeListener {
    private var mediaSession: MediaSession? = null
    private var exoPlayer: ExoPlayer? = null
    private lateinit var regionPreferences: RegionPreferences

    @UnstableApi
    override fun onCreate() {
        super.onCreate()
        regionPreferences = RegionPreferences(this)
        
        val okHttpClient = OkHttpClient.Builder()
            .followRedirects(true)
            .followSslRedirects(true)
            .retryOnConnectionFailure(true)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
            
        val dataSourceFactory = OkHttpDataSource.Factory(okHttpClient)
        val mediaSourceFactory = DefaultMediaSourceFactory(this)
            .setDataSourceFactory(dataSourceFactory)

        setupPlayer(mediaSourceFactory)
        
        regionPreferences.registerListener(this)
    }

    @UnstableApi
    private fun setupPlayer(mediaSourceFactory: DefaultMediaSourceFactory) {
        val isPowerSaving = regionPreferences.isPowerSavingEnabled
        
        // Optimize buffer for power saving (less wakeups) vs performance
        val loadControl = DefaultLoadControl.Builder()
            .setBufferDurationsMs(
                if (isPowerSaving) 60_000 else 30_000, // Min buffer
                if (isPowerSaving) 90_000 else 50_000, // Max buffer
                if (isPowerSaving) 5_000 else 2_500,   // Buffer for playback
                if (isPowerSaving) 10_000 else 5_000   // Buffer for playback after rebuffer
            )
            .build()

        exoPlayer = ExoPlayer.Builder(this)
            .setMediaSourceFactory(mediaSourceFactory)
            .setLoadControl(loadControl)
            .setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(C.AUDIO_CONTENT_TYPE_MUSIC)
                    .setUsage(C.USAGE_MEDIA)
                    .build(),
                true
            )
            .build()
            
        mediaSession = MediaSession.Builder(this, exoPlayer!!).build()
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "power_saving") {
            // Re-configure player for power saving if needed
        }
    }

    override fun onGetSession(controllerInfo: MediaSession.ControllerInfo): MediaSession? = mediaSession

    override fun onDestroy() {
        regionPreferences.unregisterListener(this)
        mediaSession?.run {
            player.release()
            release()
            mediaSession = null
        }
        super.onDestroy()
    }
}