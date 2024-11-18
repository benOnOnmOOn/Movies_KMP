package com.bz.movies.kmp.network.repository

import com.bz.movies.kmp.dto.MovieDto
import com.bz.movies.kmp.network.repository.model.MoveDetailDto

interface MovieRepository {
    suspend fun getPlayingNowMovies(): Result<List<MovieDto>>

    suspend fun getPopularMovies(page: Int): Result<List<MovieDto>>

    suspend fun getMovieDetail(movieId: Int): Result<MoveDetailDto>
}
