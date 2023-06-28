package com.bz.movies.kmp.di

import com.bz.movies.database.di.databaseModule
import com.bz.movies.database.di.repositoryModule
import com.bz.movies.kmp.network.di.commonNetworkModule
import com.bz.movies.kmp.network.di.platformNetworkModule
import com.bz.movies.presentation.screens.favorite.FavoriteScreenViewModel
import com.bz.movies.presentation.screens.playingNow.PlayingNowViewModel
import com.bz.movies.presentation.screens.popular.PopularMoviesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel { PlayingNowViewModel(get(), get()) }
    viewModel { PopularMoviesViewModel(get(), get()) }
    viewModel { FavoriteScreenViewModel(get()) }
} + commonNetworkModule + platformNetworkModule+ databaseModule + repositoryModule