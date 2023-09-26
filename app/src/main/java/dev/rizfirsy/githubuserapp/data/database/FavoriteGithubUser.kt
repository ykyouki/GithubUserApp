package dev.rizfirsy.githubuserapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class FavoriteGithubUser (
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String
) : Parcelable