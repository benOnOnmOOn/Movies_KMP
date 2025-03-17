package com.bz.movies.presentation.di

import com.bz.movies.kmp.database.di.databaseModule
import com.bz.movies.kmp.database.di.repositoryModule
import com.bz.movies.kmp.datastore.di.datastoreModule
import com.bz.movies.kmp.datastore.di.datastoreRepositoryModule
import com.bz.movies.kmp.network.di.commonNetworkModule
import com.bz.movies.kmp.network.di.platformNetworkModule
import com.bz.movies.presentation.screens.details.MovieDetailsViewModel
import com.bz.movies.presentation.screens.favorite.FavoriteScreenViewModel
import com.bz.movies.presentation.screens.playingNow.PlayingNowViewModel
import com.bz.movies.presentation.screens.popular.PopularMoviesViewModel
import com.bz.movies.presentation.screens.postflop.PostflopMainViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.lazyModule

val screensModule: List<Lazy<Module>> =
    listOf(
        lazyModule {
            viewModel { PlayingNowViewModel(get(), get(), get()) }
            viewModel { PopularMoviesViewModel(get(), get(), get()) }
            viewModel { FavoriteScreenViewModel(get()) }
            viewModel { MovieDetailsViewModel(get()) }
            viewModel { PostflopMainViewModel() }
        },
        commonNetworkModule,
        platformNetworkModule,
        databaseModule,
        repositoryModule,
        datastoreModule,
        datastoreRepositoryModule,
    )
