package dev.rizfirsy.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUser
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUserDao
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteGithubUserRepository(application: Application) {

    private val mGithubUserDao: FavoriteGithubUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoriteGithubUserRoomDatabase.getDatabase(application)
        mGithubUserDao = db.githubUserDao()
    }

    fun getAllSavedGithubUsers(): LiveData<List<FavoriteGithubUser>> =
        mGithubUserDao.getAllFavouriteGithubUsers()

    fun getByUsername(username: String): LiveData<FavoriteGithubUser>? {
        return mGithubUserDao.getUserbyUsername(username)
    }

    fun insert(user: FavoriteGithubUser) {
        executorService.execute { mGithubUserDao.insert(user) }
    }

    fun update(user: FavoriteGithubUser) {
        executorService.execute { mGithubUserDao.update(user) }
    }

    fun delete(user: FavoriteGithubUser) {
        executorService.execute { mGithubUserDao.delete(user) }
    }
}