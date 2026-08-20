package com.example.radioapp.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import com.example.radioapp.R
import com.example.radioapp.data.RegionPreferences
import com.example.radioapp.databinding.FragmentSettingsBinding
import com.example.radioapp.repository.StationRepository
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var regionPreferences: RegionPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        regionPreferences = RegionPreferences(requireContext())
        
        setupRegionSwitches()
        setupPowerSavingSwitch()
        setupLanguageButton()
        
        return binding.root
    }

    private fun setupLanguageButton() {
        val languages = listOf(
            Triple(getString(R.string.lang_albanian), "sq", "Albanian"),
            Triple(getString(R.string.lang_bosnian), "bs", "Bosnian"),
            Triple(getString(R.string.lang_bulgarian), "bg", "Bulgarian"),
            Triple(getString(R.string.lang_croatian), "hr", "Croatian"),
            Triple(getString(R.string.lang_greek), "el", "Greek"),
            Triple(getString(R.string.lang_macedonian), "mk", "Macedonian"),
            Triple(getString(R.string.lang_montenegrin), "cnr", "Montenegrin"),
            Triple(getString(R.string.lang_serbian), "sr", "Serbian"),
            Triple(getString(R.string.lang_english), "en", "English")
        ).sortedBy { it.first }

        val languageDisplayNames = languages.map { it.first }.toTypedArray()

        binding.buttonSelectLanguage.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle(R.string.label_select_language)
                .setItems(languageDisplayNames) { _, which ->
                    val selectedLocale = languages[which].second
                    
                    regionPreferences.appLanguage = selectedLocale
                    
                    // Apply language change globally using AppCompatDelegate
                    val appLocale: LocaleListCompat = LocaleListCompat.forLanguageTags(selectedLocale)
                    AppCompatDelegate.setApplicationLocales(appLocale)
                }
                .show()
        }
    }

    private fun setupPowerSavingSwitch() {
        binding.switchPowerSaving.isChecked = regionPreferences.isPowerSavingEnabled
        binding.switchPowerSaving.setOnCheckedChangeListener { _, isChecked ->
            regionPreferences.isPowerSavingEnabled = isChecked
            Toast.makeText(context, "Power Saving Mode ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupRegionSwitches() {
        val allRegions = StationRepository.allStations.asSequence().map { it.region }.distinct().toList()
        
        allRegions.forEach { region ->
            val switch = SwitchMaterial(requireContext()).apply {
                text = getLocalizedRegionName(region)
                isChecked = regionPreferences.isRegionEnabled(region)
                setPadding(0, 16, 0, 16)
                
                setOnCheckedChangeListener { _, isChecked ->
                    val enabledRegions = allRegions.filter { regionPreferences.isRegionEnabled(it) }
                    
                    if (!isChecked && (enabledRegions.size <= 1) && enabledRegions.contains(region)) {
                        this.isChecked = true
                        Toast.makeText(context, "At least one region must be enabled", Toast.LENGTH_SHORT).show()
                    } else {
                        regionPreferences.setRegionEnabled(region, isChecked)
                    }
                }
            }
            binding.regionsContainer.addView(switch)
        }
    }

    private fun getLocalizedRegionName(region: String): String {
        return when (region) {
            "Albania" -> getString(R.string.label_albania)
            "Bosnia" -> getString(R.string.label_bosnia)
            "Bulgaria" -> getString(R.string.label_bulgaria)
            "Croatia" -> getString(R.string.label_croatia)
            "Greece" -> getString(R.string.label_greece)
            "Macedonia" -> getString(R.string.label_macedonia)
            "Montenegro" -> getString(R.string.label_montenegro)
            "Serbia" -> getString(R.string.label_serbia)
            else -> region
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}