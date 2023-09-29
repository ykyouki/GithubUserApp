package dev.rizfirsy.githubuserapp.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.graphics.toColor
import androidx.lifecycle.ViewModelProvider
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences
import dev.rizfirsy.githubuserapp.data.helper.ViewModelFactory
import dev.rizfirsy.githubuserapp.data.helper.dataStore
import dev.rizfirsy.githubuserapp.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appPref = SettingsPreferences.getInstance(application.dataStore)

        val settingsViewModel = obtainViewModel(this@SettingsActivity, appPref)

        settingsViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }
        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                settingsViewModel.saveThemeSetting(true)
                binding.switchTheme.isChecked = true
            } else {
                settingsViewModel.saveThemeSetting(false)
                binding.switchTheme.isChecked = false
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity, appPref: SettingsPreferences): SettingsViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, appPref)
        return ViewModelProvider(activity, factory)[SettingsViewModel::class.java]
    }
}