package dev.rizfirsy.githubuserapp.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUser
import dev.rizfirsy.githubuserapp.data.repository.FavoriteGithubUserRepository

class FavoriteGithubUserViewModel(application: Application) : ViewModel() {

    private val mFavoriteGithubUserRepository: FavoriteGithubUserRepository =
        FavoriteGithubUserRepository(application)

    fun getAllFavouriteGithubUser(): LiveData<List<FavoriteGithubUser>> =
        mFavoriteGithubUserRepository.getAllSavedGithubUsers()
}