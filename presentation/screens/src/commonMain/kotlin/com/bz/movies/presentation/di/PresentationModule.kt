package com.bz.movies.presentation.di

import com.bz.movies.presentation.screens.details.MovieDetailsViewModel
import com.bz.movies.presentation.screens.favorite.FavoriteScreenViewModel
import com.bz.movies.presentation.screens.playingNow.PlayingNowViewModel
import com.bz.movies.presentation.screens.popular.PopularMoviesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val screensModule =
    module {
        viewModel { PlayingNowViewModel(get(), get(), get()) }
        viewModel { PopularMoviesViewModel(get(), get(), get()) }
        viewModel { FavoriteScreenViewModel(get()) }
        viewModel { MovieDetailsViewModel(get()) }
    }
