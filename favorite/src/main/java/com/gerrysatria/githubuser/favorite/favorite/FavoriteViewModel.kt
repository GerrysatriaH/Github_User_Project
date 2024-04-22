package com.gerrysatria.githubuser.favorite.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.gerrysatria.githubuser.core.domain.usecase.UserUseCase

class FavoriteViewModel(useCase: UserUseCase): ViewModel() {
    val favoriteUsers = useCase.getFavoriteUser().asLiveData()
}