package com.example.radioapp.ui.region

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.radioapp.R
import com.example.radioapp.data.RegionPreferences
import com.example.radioapp.databinding.FragmentRegionSelectionBinding

class RegionSelectionFragment : Fragment() {

    private var _binding: FragmentRegionSelectionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegionSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        val prefs = RegionPreferences(requireContext())
        
        binding.buttonAlbania.visibility = if (prefs.isRegionEnabled("Albania")) View.VISIBLE else View.GONE
        binding.buttonBosnia.visibility = if (prefs.isRegionEnabled("Bosnia")) View.VISIBLE else View.GONE
        binding.buttonBulgaria.visibility = if (prefs.isRegionEnabled("Bulgaria")) View.VISIBLE else View.GONE
        binding.buttonCroatia.visibility = if (prefs.isRegionEnabled("Croatia")) View.VISIBLE else View.GONE
        binding.buttonGreece.visibility = if (prefs.isRegionEnabled("Greece")) View.VISIBLE else View.GONE
        binding.buttonMacedonia.visibility = if (prefs.isRegionEnabled("Macedonia")) View.VISIBLE else View.GONE
        binding.buttonMontenegro.visibility = if (prefs.isRegionEnabled("Montenegro")) View.VISIBLE else View.GONE
        binding.buttonSerbia.visibility = if (prefs.isRegionEnabled("Serbia")) View.VISIBLE else View.GONE

        binding.buttonAlbania.setOnClickListener {
            navigateToRadio("Albania")
        }
        binding.buttonBosnia.setOnClickListener {
            navigateToRadio("Bosnia")
        }
        binding.buttonBulgaria.setOnClickListener {
            navigateToRadio("Bulgaria")
        }
        binding.buttonCroatia.setOnClickListener {
            navigateToRadio("Croatia")
        }
        binding.buttonGreece.setOnClickListener {
            navigateToRadio("Greece")
        }
        binding.buttonMacedonia.setOnClickListener {
            navigateToRadio("Macedonia")
        }
        binding.buttonMontenegro.setOnClickListener {
            navigateToRadio("Montenegro")
        }
        binding.buttonSerbia.setOnClickListener {
            navigateToRadio("Serbia")
        }
        binding.buttonFavorites.setOnClickListener {
            val bundle = Bundle().apply {
                putBoolean("isFavorites", true)
            }
            findNavController().navigate(R.id.nav_radio, bundle)
        }
        binding.buttonAll.setOnClickListener {
            navigateToRadio(null)
        }
        binding.buttonSettings.setOnClickListener {
            findNavController().navigate(R.id.nav_settings)
        }
    }

    private fun navigateToRadio(region: String?) {
        val bundle = Bundle().apply {
            putString("region", region)
        }
        // Using a common destination for all radio views
        findNavController().navigate(R.id.nav_radio, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}