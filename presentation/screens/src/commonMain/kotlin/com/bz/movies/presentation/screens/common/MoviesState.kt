package com.bz.movies.presentation.screens.common

internal data class MoviesState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val playingNowMovies: List<MovieItem> = emptyList(),
)

internal data class MovieDetailState(
    val isLoading: Boolean = true,
    val movieDetails: MovieItem? = null,
)
