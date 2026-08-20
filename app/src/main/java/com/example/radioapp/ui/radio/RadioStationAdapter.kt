package com.example.radioapp.ui.radio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.radioapp.R
import com.example.radioapp.data.RadioStation
import com.example.radioapp.data.RegionPreferences
import com.example.radioapp.databinding.ItemRegionHeaderBinding
import com.example.radioapp.databinding.ItemStationBinding

sealed class RadioListItem {
    data class Header(val region: String) : RadioListItem()
    data class Station(val station: RadioStation) : RadioListItem()
}

class RadioStationAdapter(
    private val items: List<RadioListItem>,
    private val regionPreferences: RegionPreferences,
    private val onStationClick: (RadioStation) -> Unit,
    private val onFavoriteClick: (RadioStation) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_STATION = 1
    }

    class HeaderViewHolder(val binding: ItemRegionHeaderBinding) : RecyclerView.ViewHolder(binding.root)
    class StationViewHolder(val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is RadioListItem.Header -> TYPE_HEADER
            is RadioListItem.Station -> TYPE_STATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(ItemRegionHeaderBinding.inflate(inflater, parent, false))
            TYPE_STATION -> StationViewHolder(ItemStationBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is RadioListItem.Header -> {
                (holder as HeaderViewHolder).binding.textViewRegionName.text = item.region
            }
            is RadioListItem.Station -> {
                val stationHolder = holder as StationViewHolder
                stationHolder.binding.textViewStationName.text = item.station.name
                
                val isFav = regionPreferences.isStationFavorite(item.station.streamUrl)
                stationHolder.binding.buttonFavorite.setImageResource(
                    if (isFav) R.drawable.ic_star_filled else R.drawable.ic_star_outline
                )
                
                // Color logic for the star - Purple for favorites
                val starColor = if (isFav) {
                    android.graphics.Color.parseColor("#6200EE") // Purple
                } else {
                    android.graphics.Color.LTGRAY
                }
                stationHolder.binding.buttonFavorite.setColorFilter(starColor)

                stationHolder.itemView.setOnClickListener { onStationClick(item.station) }
                stationHolder.binding.buttonFavorite.setOnClickListener {
                    onFavoriteClick(item.station)
                }
            }
        }
    }

    override fun getItemCount(): Int = items.size
}