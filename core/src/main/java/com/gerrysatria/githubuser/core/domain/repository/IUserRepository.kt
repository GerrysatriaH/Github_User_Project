package com.gerrysatria.githubuser.core.domain.repository

import com.gerrysatria.githubuser.core.data.Resource
import com.gerrysatria.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface IUserRepository {

    fun getAllUsers(user: String): Flow<Resource<List<User>>>
    fun getDetailUser(user: String): Flow<Resource<User>>
    fun getFollowersUser(user: String): Flow<Resource<List<User>>>
    fun getFollowingUser(user: String): Flow<Resource<List<User>>>

    fun getFavoriteUser(): Flow<List<User>>
    fun getFavoriteUserByUsername(username: String): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: String)
}