package com.bz.movies.presentation.screens.common

sealed class MovieEvent {
    data class OnMovieClicked(val movieItem: MovieItem) : MovieEvent()

    object Refresh : MovieEvent()

}

