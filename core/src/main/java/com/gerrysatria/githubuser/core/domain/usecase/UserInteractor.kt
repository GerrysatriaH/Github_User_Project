package com.gerrysatria.githubuser.core.domain.usecase

import com.gerrysatria.githubuser.core.data.Resource
import com.gerrysatria.githubuser.core.domain.model.User
import com.gerrysatria.githubuser.core.domain.repository.IUserRepository
import kotlinx.coroutines.flow.Flow

class UserInteractor(private val userRepository: IUserRepository): UserUseCase {
    override fun getAllUser(user: String): Flow<Resource<List<User>>> =
        userRepository.getAllUsers(user)

    override fun getDetailUser(user: String): Flow<Resource<User>> =
        userRepository.getDetailUser(user)

    override fun getFollowersUser(user: String): Flow<Resource<List<User>>> =
        userRepository.getFollowersUser(user)

    override fun getFollowingUser(user: String): Flow<Resource<List<User>>> =
        userRepository.getFollowingUser(user)

    override fun getFavoriteUser(): Flow<List<User>> =
        userRepository.getFavoriteUser()

    override fun getFavoriteUserByUsername(username: String): Flow<List<User>> =
        userRepository.getFavoriteUserByUsername(username)

    override suspend fun insertUser(user: User) = userRepository.insertUser(user)

    override suspend fun deleteUser(user: String) = userRepository.deleteUser(user)
}