package com.bz.network.api.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal data class PopularMoviesPageApiResponse(
    @Json(name = "page") val page: Int,
    @Json(name = "results") val movies: List<MovieApiResponse>,
    @Json(name = "total_pages") val totalPages: Int,
    @Json(name = "total_results") val totalResults: Int
)

