package com.gerrysatria.githubuser.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class ListUsersResponse (

    @field:SerializedName("items")
    val users: List<UserResponse>
)