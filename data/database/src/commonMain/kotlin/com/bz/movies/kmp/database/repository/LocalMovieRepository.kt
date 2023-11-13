package com.bz.movies.kmp.database.repository

import com.bz.movies.kmp.dto.MovieDto
import kotlinx.coroutines.flow.Flow

interface LocalMovieRepository {
    val favoritesMovies: Flow<List<MovieDto>>

    val playingNowMovies: Flow<List<MovieDto>>

    val popularMovies: Flow<List<MovieDto>>

    suspend fun insertFavoriteMovie(movieDto: MovieDto): Result<Unit>

    suspend fun deleteFavoriteMovie(movieDto: MovieDto): Result<Unit>

    suspend fun insertPlayingNowMovies(movieDto: List<MovieDto>): Result<Unit>

    suspend fun clearPlayingNowMovies(): Result<Unit>

    suspend fun insertPopularMovies(movieDto: List<MovieDto>): Result<Unit>

    suspend fun clearPopularMovies(): Result<Unit>
}
