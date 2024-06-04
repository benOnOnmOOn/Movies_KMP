package com.bz.movies.kmp.network.api.service

import com.bz.movies.kmp.network.api.model.MovieDetailsApiResponse
import com.bz.movies.kmp.network.api.model.PlayingNowMoviesApiResponse
import com.bz.movies.kmp.network.api.model.PopularMoviesPageApiResponse
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

const val BASE_URL = "https://api.themoviedb.org/3/"

expect fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient

internal class MovieService {
    private val client =
        httpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }
        }

    suspend fun getPopularMoviePage(
        apiKey: String,
        language: String,
        page: Int
    ): PopularMoviesPageApiResponse {
        val url = BASE_URL.plus("movie/popular")
        return client.get(url) {
            url {
                parameters.append("api_key", apiKey)
                parameters.append("language", language)
                parameters.append("page", page.toString())
            }
        }.body()
    }

    suspend fun getNowPlayingMovies(
        apiKey: String,
        language: String,
        page: Int
    ): PlayingNowMoviesApiResponse {
        val url = BASE_URL.plus("movie/now_playing")
        return client.get(url) {
            url {
                parameters.append("api_key", apiKey)
                parameters.append("language", language)
                parameters.append("page", page.toString())
            }
        }.body()
    }

    suspend fun getMovieDetails(
        movieId: Int,
        language: String,
        apiKey: String
    ): MovieDetailsApiResponse {
        val url = BASE_URL.plus("movie")
        return client.get(url) {
            url {
                parameters.append("api_key", apiKey)
                parameters.append("language", language)
                appendPathSegments(movieId.toString())
            }
        }.body()
    }
}
