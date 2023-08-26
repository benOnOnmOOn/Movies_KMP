package com.bz.movies.kmp.database.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.bz.movies.kmp.database.Favaorite
import com.bz.movies.kmp.database.MoviesDB2Queries
import com.bz.movies.kmp.database.PlayingNow
import com.bz.movies.kmp.database.PopularNow
import com.bz.movies.kmp.database.repository.mapper.toMovieDto
import com.bz.movies.kmp.database.repository.mapper.toMovieEntity
import com.bz.movies.kmp.database.repository.mapper.toPlayingNowMovieEntity
import com.bz.movies.kmp.database.repository.mapper.toPopularMovieEntity
import com.bz.movies.kmp.dto.MovieDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class LocalMovieRepositoryImpl(
    private val queries: MoviesDB2Queries
) : LocalMovieRepository {

    override val favoritesMovies: Flow<List<MovieDto>>
        get() =
            queries.selectAllFavaoriteMovies()
                .asFlow()
                .mapToList(Dispatchers.IO)
                .map { it.map(Favaorite::toMovieDto) }

    override val playingNowMovies: Flow<List<MovieDto>>
        get() =
            queries.selectAllPlayingNowMovies()
                .asFlow()
                .mapToList(Dispatchers.IO)
                .map { it.map(PlayingNow::toMovieDto) }

    override val popularMovies: Flow<List<MovieDto>>
        get() =
            queries.selectAllPopularNowMovies()
                .asFlow()
                .mapToList(Dispatchers.IO)
                .map { it.map(PopularNow::toMovieDto) }

    override suspend fun insertFavoriteMovie(movieDto: MovieDto): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                queries.insertFavaorite(movieDto.toMovieEntity())
            }
        }

    override suspend fun deleteFavoriteMovie(movieDto: MovieDto): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                queries.deleteFavaorite(movieDto.id.toLong())
            }
        }

    override suspend fun insertPlayingNowMovies(movieDto: List<MovieDto>): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                movieDto.forEach { queries.insertPlayingNow(it.toPlayingNowMovieEntity()) }
            }
        }

    override suspend fun clearPlayingNowMovies(): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                queries.clearPlayingNow()
            }
        }

    override suspend fun insertPopularMovies(movieDto: List<MovieDto>): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                movieDto.forEach { queries.insertPopularNow(it.toPopularMovieEntity()) }
            }
        }

    override suspend fun clearPopularMovies(): Result<Unit> =
        withContext(Dispatchers.IO) {
            runCatching {
                queries.clearPopularNow()
            }
        }
}
