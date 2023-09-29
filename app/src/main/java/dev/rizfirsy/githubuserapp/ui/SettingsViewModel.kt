package dev.rizfirsy.githubuserapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application,
                        private val preferences: SettingsPreferences): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        Log.i("isDarkModeActiveSettings", preferences.getThemeSetting().asLiveData().value.toString())
        return preferences.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            preferences.saveThemeSetting(isDarkModeActive)
        }
    }
}