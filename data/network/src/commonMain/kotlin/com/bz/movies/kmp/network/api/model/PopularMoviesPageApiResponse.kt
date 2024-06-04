package com.bz.movies.kmp.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PopularMoviesPageApiResponse(
    @SerialName("page") val page: Int,
    @SerialName("results") val movies: List<MovieApiResponse>,
    @SerialName("total_pages") val totalPages: Int,
    @SerialName("total_results") val totalResults: Int
)
