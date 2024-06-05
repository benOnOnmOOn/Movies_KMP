package com.bz.movies.kmp.database.repository.mapper

import com.bz.movies.kmp.database.Favaorite
import com.bz.movies.kmp.database.PlayingNow
import com.bz.movies.kmp.database.PopularNow
import com.bz.movies.kmp.dto.MovieDto

internal fun Favaorite.toMovieDto() =
    MovieDto(
        id = id.toInt(),
        posterUrl = posterUrl,
        title = title,
        publicationDate = publicationDate,
        language = language,
        rating = rating.toInt(),
    )

internal fun MovieDto.toMovieEntity() =
    Favaorite(
        id = id.toLong(),
        posterUrl = posterUrl,
        title = title,
        publicationDate = publicationDate,
        language = language,
        rating = rating.toLong(),
    )

internal fun PopularNow.toMovieDto() =
    MovieDto(
        id = id.toInt(),
        posterUrl = posterUrl,
        title = title,
        publicationDate = publicationDate,
        language = language,
        rating = rating.toInt(),
    )

internal fun MovieDto.toPopularMovieEntity() =
    PopularNow(
        id = id.toLong(),
        posterUrl = posterUrl,
        title = title,
        publicationDate = publicationDate,
        language = language,
        rating = rating.toLong(),
    )

internal fun PlayingNow.toMovieDto() =
    MovieDto(
        id = id.toInt(),
        posterUrl = posterUrl,
        title = title,
        publicationDate = publicationDate,
        language = language,
        rating = rating.toInt(),
    )

internal fun MovieDto.toPlayingNowMovieEntity() =
    PlayingNow(
        id = id.toLong(),
        posterUrl = posterUrl,
        title = title,
        publicationDate = publicationDate,
        language = language,
        rating = rating.toLong(),
    )
