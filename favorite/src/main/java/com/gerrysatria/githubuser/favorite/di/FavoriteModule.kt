package com.gerrysatria.githubuser.favorite.di

import com.gerrysatria.githubuser.favorite.favorite.FavoriteViewModel
import org.koin.dsl.module

val favoriteModule = module {
    single { FavoriteViewModel(get()) }
}