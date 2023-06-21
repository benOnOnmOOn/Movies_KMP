package com.bz.network.api.service

import com.bz.network.api.model.MovieDetailsApiResponse
import com.bz.network.api.model.PlayingNowMoviesApiResponse
import com.bz.network.api.model.PopularMoviesPageApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface MovieService {

    @GET("movie/popular")
    suspend fun getPopularMoviePage(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): Response<PopularMoviesPageApiResponse>

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: String,
    ): Response<PlayingNowMoviesApiResponse>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String,
        @Query("api_key") apiKey: String,
    ): Response<MovieDetailsApiResponse>

}
