package com.example.radioapp.ui.radio

import android.content.ComponentName
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.media3.common.MediaItem
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import com.example.radioapp.R
import com.example.radioapp.data.RadioStation
import com.example.radioapp.data.RegionPreferences
import com.example.radioapp.databinding.FragmentRadioBinding
import com.example.radioapp.repository.StationRepository
import com.example.radioapp.service.PlaybackService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import com.example.radioapp.data.StationManager

class RadioFragment : Fragment() {

    private var _binding: FragmentRadioBinding? = null
    private val binding get() = _binding!!
    private lateinit var regionPreferences: RegionPreferences
    private lateinit var stationManager: StationManager

    private var controllerFuture: ListenableFuture<MediaController>? = null
    private val controller: MediaController?
        get() = if (controllerFuture?.isDone == true) controllerFuture?.get() else null

    private var currentRegion: String? = null
    private var baseStations: List<RadioStation> = emptyList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRadioBinding.inflate(inflater, container, false)
        regionPreferences = RegionPreferences(requireContext())
        stationManager = StationManager(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentRegion = arguments?.getString("region")
        val isFavorites = arguments?.getBoolean("isFavorites") ?: false
        
        lifecycleScope.launch {
            val allStations = stationManager.getStations().ifEmpty { StationRepository.allStations }
            
            baseStations = allStations.filter { 
                regionPreferences.isRegionEnabled(it.region) 
            }

            if (isFavorites) {
                baseStations = baseStations.filter { regionPreferences.isStationFavorite(it.streamUrl) }
            } else if (currentRegion != null) {
                baseStations = baseStations.filter { it.region == currentRegion }
            }

            updateList(baseStations)
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val filtered = if (newText.isNullOrBlank()) {
                    baseStations
                } else {
                    baseStations.filter {
                        it.name.contains(newText, ignoreCase = true) || it.region.contains(newText, ignoreCase = true)
                    }
                }
                updateList(filtered)
                return true
            }
        })
    }

    private fun updateList(stations: List<RadioStation>) {
        val isAllRegionsView = currentRegion == null && !(arguments?.getBoolean("isFavorites") ?: false)
        val displayItems = mutableListOf<RadioListItem>()

        // 1. Add "Favorites" section at the very top for "All Regions" view, only if there are favorites
        if (isAllRegionsView) {
            val favorites = stations.filter { regionPreferences.isStationFavorite(it.streamUrl) }
                .sortedBy { it.name }
            
            if (favorites.isNotEmpty()) {
                displayItems.add(RadioListItem.Header(getString(R.string.menu_favorites)))
                displayItems.addAll(favorites.map { RadioListItem.Station(it) })
            }
        }

        // 2. Sort main list: Region first, then Favorites at the top of each region, then Name ABC
        val sortedStations = stations.sortedWith(
            compareBy<RadioStation> { it.region }
                .thenByDescending { regionPreferences.isStationFavorite(it.streamUrl) }
                .thenBy { it.name }
        )

        val groupedItems = sortedStations.groupBy { it.region }.flatMap { (region, regionStations) ->
            listOf(RadioListItem.Header(region)) + regionStations.map { RadioListItem.Station(it) }
        }
        
        displayItems.addAll(groupedItems)

        binding.recyclerViewStations.adapter = RadioStationAdapter(
            displayItems,
            regionPreferences,
            onStationClick = { station -> playStation(station) },
            onFavoriteClick = { station ->
                regionPreferences.toggleFavorite(station.streamUrl)
                val isFavorites = arguments?.getBoolean("isFavorites") ?: false
                if (isFavorites) {
                    // In Favorites-only view, filter the base list immediately
                    baseStations = StationRepository.allStations.filter { regionPreferences.isStationFavorite(it.streamUrl) }
                }
                // Refresh the UI to reflect changes and re-sort
                updateList(baseStations)
            }
        )
    }

    override fun onStart() {
        super.onStart()
        val sessionToken = SessionToken(requireContext(), ComponentName(requireContext(), PlaybackService::class.java))
        controllerFuture = MediaController.Builder(requireContext(), sessionToken).buildAsync()
    }

    private fun playStation(station: RadioStation) {
        val controller = controller ?: return
        
        val mediaItem = MediaItem.Builder()
            .setMediaId(station.streamUrl)
            .setUri(station.streamUrl)
            .setMediaMetadata(
                androidx.media3.common.MediaMetadata.Builder()
                    .setTitle(station.name)
                    .build()
            )
            .build()

        controller.setMediaItem(mediaItem)
        controller.prepare()
        controller.play()
    }

    override fun onStop() {
        super.onStop()
        controllerFuture?.let {
            MediaController.releaseFuture(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}