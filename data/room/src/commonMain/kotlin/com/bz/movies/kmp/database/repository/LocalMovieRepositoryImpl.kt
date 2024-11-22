package com.bz.movies.kmp.database.repository

import com.bz.movies.kmp.database.dao.MovieDAO
import com.bz.movies.kmp.database.dao.PlayingNowMovieDAO
import com.bz.movies.kmp.database.dao.PopularMovieDAO
import com.bz.movies.kmp.database.entity.MovieEntity
import com.bz.movies.kmp.database.entity.PlayingNowMovieEntity
import com.bz.movies.kmp.database.entity.PopularMovieEntity
import com.bz.movies.kmp.database.repository.mapper.toMovieDto
import com.bz.movies.kmp.database.repository.mapper.toMovieEntity
import com.bz.movies.kmp.database.repository.mapper.toPlayingNowMovieEntity
import com.bz.movies.kmp.database.repository.mapper.toPopularMovieEntity
import com.bz.movies.kmp.dto.MovieDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import runSuspendCatching

internal class LocalMovieRepositoryImpl(
    private val movieDAO: MovieDAO,
    private val playingNowMovieDAO: PlayingNowMovieDAO,
    private val popularMovieDAO: PopularMovieDAO,
) : LocalMovieRepository {
    override val favoritesMovies: Flow<List<MovieDto>>
        get() =
            movieDAO
                .observeAllMovies()
                .flowOn(Dispatchers.IO)
                .map { it.map(MovieEntity::toMovieDto) }

    override val playingNowMovies: Flow<List<MovieDto>>
        get() =
            playingNowMovieDAO
                .observeAllMovies()
                .flowOn(Dispatchers.IO)
                .map { it.map(PlayingNowMovieEntity::toMovieDto) }

    override val popularMovies: Flow<List<MovieDto>>
        get() =
            popularMovieDAO
                .observeAllMovies()
                .flowOn(Dispatchers.IO)
                .map { it.map(PopularMovieEntity::toMovieDto) }

    override suspend fun insertFavoriteMovie(movieDto: MovieDto): Result<Unit> =
        withContext(Dispatchers.IO) {
            runSuspendCatching {
                movieDAO.insert(movieDto.toMovieEntity())
            }
        }

    override suspend fun deleteFavoriteMovie(movieDto: MovieDto): Result<Unit> =
        withContext(Dispatchers.IO) {
            runSuspendCatching {
                movieDAO.delete(movieDto.toMovieEntity())
            }
        }

    override suspend fun insertPlayingNowMovies(movieDto: List<MovieDto>): Result<Unit> =
        withContext(Dispatchers.IO) {
            runSuspendCatching {
                playingNowMovieDAO.insert(movieDto.map { it.toPlayingNowMovieEntity() })
            }
        }

    override suspend fun clearPlayingNowMovies(): Result<Unit> =
        withContext(Dispatchers.IO) {
            runSuspendCatching {
                playingNowMovieDAO.clearTable()
            }
        }

    override suspend fun insertPopularMovies(movieDto: List<MovieDto>): Result<Unit> =
        withContext(Dispatchers.IO) {
            runSuspendCatching {
                popularMovieDAO.insert(movieDto.map { it.toPopularMovieEntity() })
            }
        }

    override suspend fun clearPopularMovies(): Result<Unit> =
        withContext(Dispatchers.IO) {
            runSuspendCatching {
                popularMovieDAO.clearTable()
            }
        }
}
