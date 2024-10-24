package com.bz.movies.presentation.mappers

import com.bz.movies.kmp.dto.MovieDto
import com.bz.movies.presentation.screens.common.MovieItem

internal fun MovieDto.toMovieItem() =
    MovieItem(
        id = id,
        posterUrl = "https://image.tmdb.org/t/p/w154/$posterUrl",
        title = title,
        releaseDate = publicationDate,
        rating = rating,
        language = language,
    )

internal fun MovieItem.toDTO() =
    MovieDto(
        rating = rating,
        language = language,
        title = title,
        publicationDate = releaseDate,
        id = id,
        posterUrl = posterUrl,
    )
