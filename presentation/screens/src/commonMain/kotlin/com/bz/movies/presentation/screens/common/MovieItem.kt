package com.bz.movies.presentation.screens.common

internal data class MovieItem(
    val id: Int,
    val posterUrl: String,
    val title: String,
    val releaseDate: String,
    val rating: Int,
    val language: String,
)
