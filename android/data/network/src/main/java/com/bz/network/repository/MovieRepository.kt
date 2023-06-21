package com.bz.network.repository

import com.bz.dto.MovieDto
import com.bz.network.repository.model.MoveDetailDto
import com.bz.network.repository.model.PopularMoviePageDto

interface MovieRepository {
    suspend fun getPlayingNowMovies(): Result<List<MovieDto>>

    suspend fun getPopularMovies(page: Int): Result<List<MovieDto>>

    suspend fun getMovieDetail(movieId: Int): Result<MoveDetailDto>

    suspend fun getPopularMoviesPage(page: Int): Result<PopularMoviePageDto>
}
