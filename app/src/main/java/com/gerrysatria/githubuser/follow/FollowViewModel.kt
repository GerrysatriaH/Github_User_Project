package com.gerrysatria.githubuser.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gerrysatria.githubuser.core.domain.usecase.UserUseCase

class FollowViewModel(private val useCase: UserUseCase) : ViewModel(){
    fun getListFollowers(user: String) = useCase.getFollowersUser(user).asLiveData()
    fun getListFollowings(user: String) = useCase.getFollowingUser(user).asLiveData()
}