package com.gerrysatria.githubuser.core.data

import com.gerrysatria.githubuser.core.data.source.local.LocalDataSource
import com.gerrysatria.githubuser.core.data.source.remote.RemoteDataSource
import com.gerrysatria.githubuser.core.data.source.remote.network.ApiResponse
import com.gerrysatria.githubuser.core.data.source.remote.response.UserResponse
import com.gerrysatria.githubuser.core.domain.model.User
import com.gerrysatria.githubuser.core.domain.repository.IUserRepository
import com.gerrysatria.githubuser.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : IUserRepository {
    override fun getAllUsers(user: String): Flow<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(){
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> =
                DataMapper.mapResponsesToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getUsers(user)

        }.asFlow()

    override fun getDetailUser(user: String): Flow<Resource<User>> =
        object : NetworkOnlyResource<User, UserResponse>(){
            override fun loadFromNetwork(data: UserResponse): Flow<User> =
                DataMapper.mapResponseToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> =
                remoteDataSource.getDetailUser(user)

        }.asFlow()

    override fun getFollowersUser(user: String): Flow<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(){
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> =
                DataMapper.mapResponsesToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowersUser(user)

        }.asFlow()

    override fun getFollowingUser(user: String): Flow<Resource<List<User>>> =
        object : NetworkOnlyResource<List<User>, List<UserResponse>>(){
            override fun loadFromNetwork(data: List<UserResponse>): Flow<List<User>> =
                DataMapper.mapResponsesToDomain(data)

            override suspend fun createCall(): Flow<ApiResponse<List<UserResponse>>> =
                remoteDataSource.getFollowingsUser(user)

        }.asFlow()

    override fun getFavoriteUser(): Flow<List<User>> =
        localDataSource.getFavoriteUsers().map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override fun getFavoriteUserByUsername(username: String): Flow<List<User>> =
        localDataSource.getFavoriteUserByUsername(username).map {
            DataMapper.mapEntitiesToDomain(it)
        }

    override suspend fun insertUser(user: User) {
        val domain = DataMapper.mapDomainToEntity(user)
        return localDataSource.insertUser(domain)
    }

    override suspend fun deleteUser(user: String) =
        localDataSource.deleteUser(user)
}