package com.bz.movies.database.repository.mapper

import com.bz.dto.MovieDto
import com.bz.movies.database.entity.MovieEntity
import com.bz.movies.database.entity.PlayingNowMovieEntity
import com.bz.movies.database.entity.PopularMovieEntity

internal fun MovieEntity.toMovieDto() = MovieDto(
    id = id.toInt(),
    posterUrl = posterUrl,
    title = title,
    publicationDate = publicationDate,
    language = language,
    rating = rating,
)

internal fun MovieDto.toMovieEntity() = MovieEntity(
    id = id.toLong(),
    posterUrl = posterUrl,
    title = title,
    publicationDate = publicationDate,
    language = language,
    rating = rating,
)

internal fun PopularMovieEntity.toMovieDto() = MovieDto(
    id = id.toInt(),
    posterUrl = posterUrl,
    title = title,
    publicationDate = publicationDate,
    language = language,
    rating = rating,
)

internal fun MovieDto.toPopularMovieEntity() = PopularMovieEntity(
    id = id.toLong(),
    posterUrl = posterUrl,
    title = title,
    publicationDate = publicationDate,
    language = language,
    rating = rating,
)

internal fun PlayingNowMovieEntity.toMovieDto() = MovieDto(
    id = id.toInt(),
    posterUrl = posterUrl,
    title = title,
    publicationDate = publicationDate,
    language = language,
    rating = rating,
)

internal fun MovieDto.toPlayingNowMovieEntity() = PlayingNowMovieEntity(
    id = id.toLong(),
    posterUrl = posterUrl,
    title = title,
    publicationDate = publicationDate,
    language = language,
    rating = rating,
)


