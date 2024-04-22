package com.gerrysatria.githubuser.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gerrysatria.githubuser.core.domain.usecase.UserUseCase

class MainViewModel(private val userUseCase: UserUseCase) : ViewModel() {
    fun findUser(user:String = "Arif") = userUseCase.getAllUser(user).asLiveData()
}