package com.adrianusid.githubapp.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorite")
@Parcelize
data class Favorite (

    @PrimaryKey
    @ColumnInfo(name = "login")
    var login: String,

    @ColumnInfo(name = "url")
    var url: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatar_url: String? = null
) : Parcelable