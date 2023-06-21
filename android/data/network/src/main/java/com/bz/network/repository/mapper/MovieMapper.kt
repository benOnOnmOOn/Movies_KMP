package com.bz.network.repository.mapper

import com.bz.dto.MovieDto
import com.bz.network.api.model.MovieApiResponse
import com.bz.network.api.model.MovieDetailsApiResponse
import com.bz.network.api.model.PlayingNowMoviesApiResponse
import com.bz.network.api.model.PopularMoviesPageApiResponse
import com.bz.network.repository.model.MoveDetailDto
import com.bz.network.repository.model.PopularMoviePageDto

const val VOTE_MULTIPLER = 10
internal fun MovieApiResponse.toPopularMovieDto() = MovieDto(
    id = id,
    posterUrl = posterPath.orEmpty(),
    title = title.orEmpty(),
    publicationDate = releaseDate.orEmpty(),
    language = originalLanguage.orEmpty(),
    rating = voteAverage?.let { it * VOTE_MULTIPLER }?.toInt() ?: 0,
)

internal fun PopularMoviesPageApiResponse.toPopularMovieDto() =
    movies.map { it.toPopularMovieDto() }

internal fun MovieApiResponse.toPlayingNowMovieDto() = MovieDto(
    id = id,
    posterUrl = posterPath.orEmpty(),
    title = title.orEmpty(),
    publicationDate = releaseDate.orEmpty(),
    language = originalLanguage.orEmpty(),
    rating = voteAverage?.let { it * VOTE_MULTIPLER }?.toInt() ?: 0,
)

internal fun PlayingNowMoviesApiResponse.toMovieDto() =
    movies.map { it.toPlayingNowMovieDto() }

internal fun MovieDetailsApiResponse.toMovieDetailDto() = MoveDetailDto(
    id = id,
    posterUrl = posterPath.orEmpty(),
    publicationDate = releaseDate.orEmpty(),
    language = originalLanguage.orEmpty(),
    title = title.orEmpty(),
    genre = genres?.map { it.name }?.toSet() ?: emptySet(),
    overview = overview.orEmpty(),
)

internal fun PopularMoviesPageApiResponse.toPopularMoviePageDto() = PopularMoviePageDto(
    page = page,
    totalPages = totalPages,
    totalResults = totalResults,
    popularMovies = movies.map { it.toPopularMovieDto() },
)
