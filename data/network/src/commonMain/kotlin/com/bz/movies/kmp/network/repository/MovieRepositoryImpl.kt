package com.bz.movies.kmp.network.repository

import com.bz.movies.kmp.dto.MovieDto
import com.bz.movies.kmp.network.api.model.MovieDetailsApiResponse
import com.bz.movies.kmp.network.api.model.PlayingNowMoviesApiResponse
import com.bz.movies.kmp.network.api.model.PopularMoviesPageApiResponse
import com.bz.movies.kmp.network.api.service.MovieService
import com.bz.movies.kmp.network.repository.mapper.toMovieDetailDto
import com.bz.movies.kmp.network.repository.mapper.toMovieDto
import com.bz.movies.kmp.network.repository.mapper.toPopularMovieDto
import com.bz.movies.kmp.network.repository.mapper.toPopularMoviePageDto
import com.bz.movies.kmp.network.repository.model.MoveDetailDto
import com.bz.movies.kmp.network.repository.model.PopularMoviePageDto
import com.bz.movies.kmp.network.utils.InternetConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

private const val AUTH_KEY = "55957fcf3ba81b137f8fc01ac5a31fb5"
private const val LANGUAGE = "en-US"

internal class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val internetConnectionChecker: InternetConnection
) : MovieRepository {

    override suspend fun getPlayingNowMovies(): Result<List<MovieDto>> =
        executeApiCall(PlayingNowMoviesApiResponse::toMovieDto) {
            movieService.getNowPlayingMovies(
                apiKey = AUTH_KEY,
                language = LANGUAGE,
                page = "undefine"
            )
        }

    override suspend fun getPopularMovies(page: Int): Result<List<MovieDto>> =
        executeApiCall(PopularMoviesPageApiResponse::toPopularMovieDto) {
            movieService.getPopularMoviePage(
                apiKey = AUTH_KEY,
                language = LANGUAGE,
                page = page
            )
        }

    override suspend fun getMovieDetail(movieId: Int): Result<MoveDetailDto> =
        executeApiCall(MovieDetailsApiResponse::toMovieDetailDto) {
            movieService.getMovieDetails(
                apiKey = AUTH_KEY,
                language = LANGUAGE,
                movieId = movieId
            )
        }

    override suspend fun getPopularMoviesPage(page: Int): Result<PopularMoviePageDto> =
        executeApiCall(PopularMoviesPageApiResponse::toPopularMoviePageDto) {
            movieService.getPopularMoviePage(
                apiKey = AUTH_KEY,
                language = LANGUAGE,
                page = page
            )
        }

    private suspend inline fun <T, R> executeApiCall(
        crossinline mapper: (T) -> R,
        crossinline apiCall: suspend () -> T
    ): Result<R> {
        if (!internetConnectionChecker.isConnected) {
            return Result.failure(NoInternetException())
        }

        return withContext(Dispatchers.IO) {
            runCatching {
                val response = apiCall()
                mapper(response)
            }
        }
    }
}
