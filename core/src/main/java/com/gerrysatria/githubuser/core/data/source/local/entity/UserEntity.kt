package com.gerrysatria.githubuser.core.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity (

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "username")
    val login: String,

    @ColumnInfo(name = "avatar")
    val avatarUrl: String?,

    @ColumnInfo(name = "name")
    val name: String?,

    @ColumnInfo(name = "followers")
    var followers: Int?,

    @ColumnInfo(name = "following")
    var following: Int?
)