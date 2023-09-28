package dev.rizfirsy.githubuserapp.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteGithubUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteGithubUser)

    @Update
    fun update(user: FavoriteGithubUser)

    @Delete
    fun delete(user: FavoriteGithubUser)

    @Query("SELECT * from favoritegithubuser ORDER BY username ASC")
    fun getAllFavouriteGithubUsers() :LiveData<List<FavoriteGithubUser>>

    @Query("SELECT * FROM favoritegithubuser WHERE username = :username")
    fun getUserbyUsername(username: String) :LiveData<FavoriteGithubUser>?
}