package dev.rizfirsy.githubuserapp.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import dev.rizfirsy.githubuserapp.data.database.GithubUser
import dev.rizfirsy.githubuserapp.data.database.GithubUserDao
import dev.rizfirsy.githubuserapp.data.database.GithubUserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class GithubUserRepository(application: Application) {

    private val mGithubUserDao: GithubUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = GithubUserRoomDatabase.getDatabase(application)
        mGithubUserDao = db.githubUserDao()
    }

    fun getAllSavedGithubUsers(): LiveData<List<GithubUser>> =mGithubUserDao.getAllSavedGithubUsers()

    fun insert(user: GithubUser) {
        executorService.execute { mGithubUserDao.insert(user) }
    }

    fun update(user: GithubUser) {
        executorService.execute { mGithubUserDao.update(user) }
    }

    fun delete(user: GithubUser) {
        executorService.execute { mGithubUserDao.delete(user) }
    }

}