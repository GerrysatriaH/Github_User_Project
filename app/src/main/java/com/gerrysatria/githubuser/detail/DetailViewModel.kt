package com.gerrysatria.githubuser.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.gerrysatria.githubuser.core.domain.model.User
import com.gerrysatria.githubuser.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: UserUseCase) : ViewModel(){

    fun getDetailUser(user: String) = userUseCase.getDetailUser(user).asLiveData()
    fun getFavoriteUserByUsername(user: String) = userUseCase.getFavoriteUserByUsername(user).asLiveData()
    fun insertFavorite(user: User) = viewModelScope.launch {
        userUseCase.insertUser(user)
    }
    fun deleteFavorite(user: String) = viewModelScope.launch {
        userUseCase.deleteUser(user)
    }
}