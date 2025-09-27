package com.bz.movies.kmp.network.repository

import com.bz.movies.kmp.dto.MovieDto
import com.bz.movies.kmp.network.repository.model.MoveDetailDto

public interface MovieRepository {
    public suspend fun getPlayingNowMovies(): Result<List<MovieDto>>

    public suspend fun getPopularMovies(page: Int): Result<List<MovieDto>>

    public suspend fun getMovieDetail(movieId: Int): Result<MoveDetailDto>
}
