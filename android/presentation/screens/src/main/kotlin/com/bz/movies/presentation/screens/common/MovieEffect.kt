package com.bz.movies.presentation.screens.common

sealed class MovieEffect {
    object NetworkConnectionError : MovieEffect()

    object UnknownError : MovieEffect()
}
