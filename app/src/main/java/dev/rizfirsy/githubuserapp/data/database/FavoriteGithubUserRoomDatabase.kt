package dev.rizfirsy.githubuserapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteGithubUser::class], version = 1)
abstract class FavoriteGithubUserRoomDatabase: RoomDatabase() {
    abstract fun githubUserDao(): FavoriteGithubUserDao

    companion object{
        @Volatile
        private var INSTANCE: FavoriteGithubUserRoomDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FavoriteGithubUserRoomDatabase {
            if (INSTANCE == null ) {
                synchronized(FavoriteGithubUserRoomDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteGithubUserRoomDatabase::class.java, "favorite_github_user_database")
                        .build()
                }
            }
            return INSTANCE as FavoriteGithubUserRoomDatabase
        }
    }


}