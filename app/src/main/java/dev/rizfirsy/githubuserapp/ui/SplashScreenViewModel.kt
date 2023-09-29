package dev.rizfirsy.githubuserapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences
import kotlinx.coroutines.launch

class SplashScreenViewModel(application: Application,
                            private val preferences: SettingsPreferences): ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }
}