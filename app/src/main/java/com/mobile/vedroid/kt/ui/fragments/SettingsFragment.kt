package com.mobile.vedroid.kt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mobile.vedroid.kt.MobileApplication
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.databinding.FragmentSettingsBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.storage.AccountSPStore
import com.mobile.vedroid.kt.storage.SettingsDSStore
import com.mobile.vedroid.kt.ui.SingleActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding get() = _binding!!

    private val ds = SettingsDSStore()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugging("HI")

        binding.btnLogOut.setOnClickListener {
            debugging("Click to LOG OUT and return to start")
            AccountSPStore().clearAccount() // LOG OUT
            findNavController().navigate(R.id.action_screen_settings_to_start)
        }
        binding.btnCloseSettings.setOnClickListener {
            debugging("Click return to start")
            findNavController().navigate(R.id.action_screen_settings_to_start)
        }

        binding.checkboxRuLanguage.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) debugging("Check always Russian")
            else debugging("Uncheck always Russian, use default Locale")

            // save checkboxRuLanguage
            lifecycleScope.launch {
                ds.saveAlwaysRuLanguage(isChecked)
                MobileApplication.setLocaleAlwaysRu(isChecked)
            }

        }
        binding.modeToggle.addOnButtonCheckedListener { toggleButton, checkedId, isChecked ->
            if (isChecked){
                val mode = when (checkedId) {
                    R.id.btn_dark -> {
                        debugging("Checked Dark mode")
                        AppCompatDelegate.MODE_NIGHT_YES
                    }
                    R.id.btn_light -> {
                        debugging("Checked Light mode")
                        AppCompatDelegate.MODE_NIGHT_NO
                    }
                    else -> {
                        debugging("Checked System Dark/Light mode")
                        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                    }
                }

                // save mode
                lifecycleScope.launch {
                    ds.saveLightOrNightMode(mode)
                    AppCompatDelegate.setDefaultNightMode(mode)
                }
            }
        }

        // read old mode & checkboxRuLanguage
        lifecycleScope.launch {
            binding.checkboxRuLanguage.isChecked = ds.isAlwaysRuLanguage().first()
            when(ds.loadLightOrNightMode().first()){
                AppCompatDelegate.MODE_NIGHT_YES -> binding.modeToggle.check(R.id.btn_dark)
                AppCompatDelegate.MODE_NIGHT_NO -> binding.modeToggle.check(R.id.btn_light)
                else -> binding.modeToggle.check(R.id.btn_system)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}