package com.bz.movies.presentation.screens.common

internal sealed class MovieEffect {
    data object NetworkConnectionError : MovieEffect()

    data object UnknownError : MovieEffect()
}
