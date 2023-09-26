package dev.rizfirsy.githubuserapp.data.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
class GithubUser (
    @PrimaryKey
    @ColumnInfo(name = "username")
    var username: String
) : Parcelable