package com.gerrysatria.githubuser.di

import com.gerrysatria.githubuser.home.MainViewModel
import com.gerrysatria.githubuser.core.domain.usecase.UserInteractor
import com.gerrysatria.githubuser.core.domain.usecase.UserUseCase
import com.gerrysatria.githubuser.detail.DetailViewModel
import com.gerrysatria.githubuser.follow.FollowViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FollowViewModel(get()) }
}