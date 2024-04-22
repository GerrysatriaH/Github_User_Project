package com.gerrysatria.githubuser.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val avatarUrl: String?,
    val login: String,
    val name: String?,
    val follower: Int?,
    val following: Int?
) : Parcelable