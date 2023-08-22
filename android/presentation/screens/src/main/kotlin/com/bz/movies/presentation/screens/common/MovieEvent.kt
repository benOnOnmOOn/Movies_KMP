package com.bz.movies.presentation.screens.common

sealed class MovieEvent {
    data class OnMovieClicked(val movieItem: MovieItem) : MovieEvent()

    data object Refresh : MovieEvent()

}

