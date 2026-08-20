package com.example.radioapp

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.ComponentName
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.example.radioapp.data.RegionPreferences
import com.example.radioapp.databinding.ActivityMainBinding
import com.example.radioapp.service.PlaybackService
import com.google.android.material.navigation.NavigationView
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

class MainActivity : AppCompatActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var regionPreferences: RegionPreferences

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController?
        get() = if (controllerFuture?.isDone == true) controllerFuture?.get() else null

    private var rotateAnimator: ObjectAnimator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMain.toolbar)
        
        regionPreferences = RegionPreferences(this)

        val navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment?)!!
        val navController = navHostFragment.navController

        // Only Region Selection is top-level. Others will have a back button.
        appBarConfiguration = if (binding.drawerLayout != null) {
            AppBarConfiguration(
                setOf(R.id.nav_region_selection),
                binding.drawerLayout
            )
        } else {
            AppBarConfiguration(setOf(R.id.nav_region_selection))
        }
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.navView?.setupWithNavController(navController)

        // Manual drawer toggle logic for better accessibility and Honor device compatibility
        binding.appBarMain.toolbar.setNavigationOnClickListener {
            val isTopLevel = appBarConfiguration.topLevelDestinations.contains(navController.currentDestination?.id)
            if (isTopLevel && binding.drawerLayout != null) {
                binding.drawerLayout?.openDrawer(androidx.core.view.GravityCompat.START)
            } else {
                navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
            }
        }

        setupMediaController()
        
        binding.appBarMain.buttonMiniPlayerPlayPause.setOnClickListener {
            controller?.let {
                if (it.isPlaying) it.pause() else it.play()
            }
        }
        
        binding.appBarMain.buttonMiniPlayerStop.setOnClickListener {
            controller?.let {
                it.stop()
                it.clearMediaItems()
            }
        }
    }

    private fun setupMediaController() {
        val sessionToken = SessionToken(this, ComponentName(this, PlaybackService::class.java))
        controllerFuture = MediaController.Builder(this, sessionToken).buildAsync()
        controllerFuture?.addListener({
            val controller = controller ?: return@addListener
            controller.addListener(object : Player.Listener {
                override fun onIsPlayingChanged(isPlaying: Boolean) {
                    updateMiniPlayer()
                }
                override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
                    updateMiniPlayer()
                }
            })
            updateMiniPlayer()
        }, MoreExecutors.directExecutor())
    }

    private fun updateMiniPlayer() {
        val controller = controller ?: return
        val isPlaying = controller.isPlaying
        val mediaItem = controller.currentMediaItem

        if (mediaItem != null) {
            binding.appBarMain.miniPlayer.visibility = View.VISIBLE
            binding.appBarMain.textViewMiniPlayerStationName.text = mediaItem.mediaMetadata.title ?: "Unknown"
            binding.appBarMain.buttonMiniPlayerPlayPause.setImageResource(
                if (isPlaying) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play
            )
            
            if (isPlaying && !regionPreferences.isPowerSavingEnabled) {
                if (rotateAnimator == null) {
                    rotateAnimator = ObjectAnimator.ofFloat(binding.appBarMain.imageViewMiniPlayerLogo, View.ROTATION, 0f, 360f).apply {
                        duration = 3000
                        repeatCount = ValueAnimator.INFINITE
                        interpolator = LinearInterpolator()
                        start()
                    }
                } else if (rotateAnimator?.isRunning == false) {
                    rotateAnimator?.start()
                }
            } else {
                rotateAnimator?.cancel()
                rotateAnimator = null
                binding.appBarMain.imageViewMiniPlayerLogo.rotation = 0f
            }
        } else {
            binding.appBarMain.miniPlayer.visibility = View.GONE
            rotateAnimator?.cancel()
            rotateAnimator = null
        }
    }

    override fun onStart() {
        super.onStart()
        regionPreferences.registerListener(this)
        updateNavMenu()
    }

    override fun onStop() {
        super.onStop()
        regionPreferences.unregisterListener(this)
    }

    private fun updateNavMenu() {
        val navView = binding.navView ?: return
        val menu = navView.menu
        
        menu.findItem(R.id.nav_albania)?.isVisible = regionPreferences.isRegionEnabled("Albania")
        menu.findItem(R.id.nav_bosnia)?.isVisible = regionPreferences.isRegionEnabled("Bosnia")
        menu.findItem(R.id.nav_bulgaria)?.isVisible = regionPreferences.isRegionEnabled("Bulgaria")
        menu.findItem(R.id.nav_croatia)?.isVisible = regionPreferences.isRegionEnabled("Croatia")
        menu.findItem(R.id.nav_greece)?.isVisible = regionPreferences.isRegionEnabled("Greece")
        menu.findItem(R.id.nav_macedonia)?.isVisible = regionPreferences.isRegionEnabled("Macedonia")
        menu.findItem(R.id.nav_montenegro)?.isVisible = regionPreferences.isRegionEnabled("Montenegro")
        menu.findItem(R.id.nav_serbia)?.isVisible = regionPreferences.isRegionEnabled("Serbia")
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (::binding.isInitialized) {
            updateNavMenu()
            if (key == "power_saving") {
                updateMiniPlayer()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        val navView: NavigationView? = findViewById(R.id.nav_view)
        if (navView == null) {
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}