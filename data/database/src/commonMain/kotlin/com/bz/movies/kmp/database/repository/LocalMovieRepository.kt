package com.bz.movies.kmp.database.repository

import com.bz.movies.kmp.dto.MovieDto
import kotlinx.coroutines.flow.Flow

public interface LocalMovieRepository {
    public val favoritesMovies: Flow<List<MovieDto>>

    public  val playingNowMovies: Flow<List<MovieDto>>

    public val popularMovies: Flow<List<MovieDto>>

    public  suspend fun insertFavoriteMovie(movieDto: MovieDto): Result<Unit>

    public suspend fun deleteFavoriteMovie(movieDto: MovieDto): Result<Unit>

    public  suspend fun insertPlayingNowMovies(movieDto: List<MovieDto>): Result<Unit>

    public  suspend fun clearPlayingNowMovies(): Result<Unit>

    public suspend fun insertPopularMovies(movieDto: List<MovieDto>): Result<Unit>

    public  suspend fun clearPopularMovies(): Result<Unit>
}
