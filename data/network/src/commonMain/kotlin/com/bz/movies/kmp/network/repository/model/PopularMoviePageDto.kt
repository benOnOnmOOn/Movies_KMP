package com.bz.movies.kmp.network.repository.model

import com.bz.movies.kmp.dto.MovieDto

data class PopularMoviePageDto(
    val page: Int,
    val totalPages: Int,
    val totalResults: Int,
    val popularMovies: List<MovieDto>
)
