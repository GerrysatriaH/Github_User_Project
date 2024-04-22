package com.gerrysatria.githubuser.core.data.source.remote.network

import com.gerrysatria.githubuser.core.data.source.remote.response.UserResponse
import com.gerrysatria.githubuser.core.data.source.remote.response.ListUsersResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    suspend fun findUsers(@Query("q") q: String): ListUsersResponse

    @GET("users/{username}")
    suspend fun getDetailUser(@Path("username") username: String): UserResponse

    @GET("users/{username}/followers")
    suspend fun getListFollowersUser(@Path("username") username: String) : List<UserResponse>

    @GET("users/{username}/following")
    suspend fun getListFollowingsUser(@Path("username") username: String) : List<UserResponse>
}