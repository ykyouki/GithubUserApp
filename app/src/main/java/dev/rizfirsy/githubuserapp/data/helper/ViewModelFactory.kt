package dev.rizfirsy.githubuserapp.data.helper

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dev.rizfirsy.githubuserapp.ui.FavoriteGithubUserViewModel
import dev.rizfirsy.githubuserapp.ui.MainViewModel
import dev.rizfirsy.githubuserapp.ui.SettingsViewModel
import dev.rizfirsy.githubuserapp.ui.SplashScreenViewModel
import dev.rizfirsy.githubuserapp.ui.UserDetailViewModel

class ViewModelFactory private constructor(
    private val mApplication: Application,
    private val mPreferences: SettingsPreferences):ViewModelProvider.NewInstanceFactory()
{
    companion object{
        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        @JvmStatic
        fun getInstance(application: Application,
                        preferences: SettingsPreferences): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(ViewModelFactory::class.java) {
                    INSTANCE = ViewModelFactory(application, preferences)
                }
            }
            return INSTANCE as ViewModelFactory
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(mApplication, mPreferences) as T
        } else if (modelClass.isAssignableFrom(FavoriteGithubUserViewModel::class.java)) {
            return FavoriteGithubUserViewModel(mApplication, mPreferences) as T
        } else if (modelClass.isAssignableFrom(UserDetailViewModel::class.java)) {
            return UserDetailViewModel(mApplication, mPreferences) as T
        } else if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(mApplication, mPreferences) as T
        } else if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
            return SettingsViewModel(mApplication, mPreferences) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}