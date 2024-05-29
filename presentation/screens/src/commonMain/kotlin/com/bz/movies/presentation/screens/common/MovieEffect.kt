package com.bz.movies.presentation.screens.common

sealed class MovieEffect {
    data object NetworkConnectionError : MovieEffect()

    data object UnknownError : MovieEffect()
}
