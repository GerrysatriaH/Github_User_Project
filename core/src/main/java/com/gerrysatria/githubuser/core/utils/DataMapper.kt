package com.gerrysatria.githubuser.core.utils

import com.gerrysatria.githubuser.core.data.source.local.entity.UserEntity
import com.gerrysatria.githubuser.core.data.source.remote.response.UserResponse
import com.gerrysatria.githubuser.core.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    fun mapResponsesToDomain(input: List<UserResponse>): Flow<List<User>> {
        val userList = ArrayList<User>()
        input.map { userResponse ->
            val user = User(
                avatarUrl = userResponse.avatarUrl,
                login = userResponse.login,
                name = userResponse.name,
                following = userResponse.following,
                follower = userResponse.followers
            )
            userList.add(user)
        }
        return flowOf(userList)
    }

    fun mapResponseToDomain(input: UserResponse): Flow<User> =
        flowOf(
            User (
            avatarUrl = input.avatarUrl,
            login = input.login,
            name = input.name,
            following = input.following,
            follower = input.followers
        ))

    fun mapEntitiesToDomain(input: List<UserEntity>): List<User> =
        input.map { userEntity ->
            User(
                avatarUrl = userEntity.avatarUrl,
                login = userEntity.login,
                name = userEntity.name,
                following = userEntity.following,
                follower = userEntity.followers
            )
        }

    fun mapDomainToEntity(input: User) =
        UserEntity(
            avatarUrl = input.avatarUrl,
            login = input.login,
            name = input.name,
            following = input.following,
            followers = input.follower
        )
}