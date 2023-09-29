package dev.rizfirsy.githubuserapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUser
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences
import dev.rizfirsy.githubuserapp.data.repository.FavoriteGithubUserRepository
import kotlinx.coroutines.launch

class FavoriteGithubUserViewModel(application: Application,
                                  private val preferences: SettingsPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return preferences.getThemeSetting().asLiveData()
    }

    private val mFavoriteGithubUserRepository: FavoriteGithubUserRepository =
        FavoriteGithubUserRepository(application)

    fun getAllFavouriteGithubUser(): LiveData<List<FavoriteGithubUser>> =
        mFavoriteGithubUserRepository.getAllSavedGithubUsers()
}