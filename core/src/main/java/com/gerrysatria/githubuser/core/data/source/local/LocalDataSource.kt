package com.gerrysatria.githubuser.core.data.source.local

import com.gerrysatria.githubuser.core.data.source.local.entity.UserEntity
import com.gerrysatria.githubuser.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {
    fun getFavoriteUsers() : Flow<List<UserEntity>> = userDao.getFavoriteUsers()
    fun getFavoriteUserByUsername(user: String) : Flow<List<UserEntity>> = userDao.getFavoriteUserByUsername(user)
    suspend fun insertUser(user: UserEntity) = userDao.insertUser(user)
    suspend fun deleteUser(user: String) = userDao.deleteUser(user)
}