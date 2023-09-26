package dev.rizfirsy.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUser
import dev.rizfirsy.githubuserapp.data.helper.ViewModelFactory
import dev.rizfirsy.githubuserapp.databinding.ActivityFavoriteGithubUserBinding

class FavoriteGithubUserActivity : AppCompatActivity() {

    private lateinit var favoriteGithubUserViewModel: FavoriteGithubUserViewModel

    private var _activityFavoriteGithubUserBinding: ActivityFavoriteGithubUserBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityFavoriteGithubUserBinding = ActivityFavoriteGithubUserBinding.inflate(layoutInflater)
        setContentView(_activityFavoriteGithubUserBinding?.root)


        val layoutManager = LinearLayoutManager(this)
        _activityFavoriteGithubUserBinding?.rvGithubUser?.layoutManager =layoutManager

        favoriteGithubUserViewModel = obtainViewModel(this@FavoriteGithubUserActivity)
        favoriteGithubUserViewModel.getAllFavouriteGithubUser().observe(this){
                user -> if (user != null) {
                addUserToFavorite(user)
            }
        }
    }

    private fun addUserToFavorite(favoriteUser: List<FavoriteGithubUser>) {
        val adapter = FavoriteGithubUserAdapter(favoriteUser)
        _activityFavoriteGithubUserBinding?.rvGithubUser?.adapter = adapter
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteGithubUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteGithubUserViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteGithubUserBinding = null
    }
}