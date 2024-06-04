package com.bz.movies.presentation.screens.common

data class MoviesState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val playingNowMovies: List<MovieItem> = emptyList()
)

data class MovieDetailState(
    val isLoading: Boolean = true,
    val movieDetails: MovieItem? = null
)
