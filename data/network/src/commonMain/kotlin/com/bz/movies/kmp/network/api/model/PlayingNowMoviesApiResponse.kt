package com.bz.movies.kmp.network.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PlayingNowMoviesApiResponse(
     @SerialName( "dates") val dates: Dates,
     @SerialName( "page") val page: Int,
     @SerialName( "results") val movies: List<MovieApiResponse>,
     @SerialName( "total_pages") val totalPages: Int,
     @SerialName( "total_results") val totalResults: Int
)

@Serializable
internal data class Dates(
     @SerialName( "maximum") val maximum: String,
     @SerialName( "minimum") val minimum: String,
)
