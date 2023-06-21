package com.bz.network.api

import com.bz.network.api.model.BelongsToCollection
import com.bz.network.api.model.Genre
import com.bz.network.api.model.MovieDetailsApiResponse
import com.bz.network.api.model.PlayingNowMoviesApiResponse
import com.bz.network.api.model.PopularMoviesPageApiResponse
import com.bz.network.api.model.ProductionCompany
import com.bz.network.api.model.ProductionCountry
import com.bz.network.api.model.SpokenLanguage
import com.bz.network.api.service.MovieService
import com.bz.network.utils.enqueueFromFile
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class ApiTest {

    private val mockWebServer = MockWebServer()

    private val retrofit = Retrofit
        .Builder()
        .client(OkHttpClient())
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(mockWebServer.url("/"))
        .build()

    private val movieService: MovieService = retrofit.create(MovieService::class.java)

//    @Before
//    fun setup() {
//        mockWebServer.start(8080)
//    }
//
//    @After
//    fun teardown() {
//        mockWebServer.shutdown()
//    }

    @Test
    fun `getMovieDetails check json parsing using sample json`() = runTest {
        mockWebServer.enqueueFromFile("movie_details.json")

        val response =
            movieService.getMovieDetails(language = "en-US", apiKey = "api-key", movieId = 23)
        assertTrue(response.isSuccessful)

        assertTrue(response.body() is MovieDetailsApiResponse)
        val body = response.body()!!
        assertEquals(EXPECTED_MOVIE_DETAILS, body)
    }

    @Test
    fun `getNowPlayingMovies check json parsing using sample json`() = runTest {
        mockWebServer.enqueueFromFile("now_playing_movies.json")

        val response =
            movieService.getNowPlayingMovies(language = "en-US", apiKey = "api-key", page = "1")
        assertTrue(response.isSuccessful)

        assertTrue(response.body() is PlayingNowMoviesApiResponse)

    }

    @Test
    fun `getPopularMoviePage check json parsing using sample json`() = runTest {
        mockWebServer.enqueueFromFile("popular_movies.json")

        val response =
            movieService.getPopularMoviePage(language = "en-US", apiKey = "api-key", page = 2)
        assertTrue(response.isSuccessful)

        assertTrue(response.body() is PopularMoviesPageApiResponse)

    }

    companion object {
        val EXPECTED_MOVIE_DETAILS = MovieDetailsApiResponse(
            adult = false,
            backdropPath = "/srYya1ZlI97Au4jUYAktDe3avyA.jpg",
            belongsToCollection = BelongsToCollection(
                id = 468552,
                name = "Wonder Woman Collection",
                posterPath = "/8AQRfTuTHeFTddZN4IUAqprN8Od.jpg",
                backdropPath = "/n9KlvCOBFDmSyw3BgNrkUkxMFva.jpg",
            ),
            budget = 200000000,
            genres = listOf(
                Genre(id = 14, name = "Fantasy"),
                Genre(id = 28, name = "Action"),
                Genre(id = 12, name = "Adventure")
            ),
            homepage = "https://www.warnerbros.com/movies/wonder-woman-1984",
            id = 464052,
            imdbId = "tt7126948",
            originalLanguage = "en",
            originalTitle = "Wonder Woman 1984",
            overview = "A botched store robbery places Wonder Woman in a global battle against a powerful and mysterious ancient force that puts her powers in jeopardy.",
            popularity = 728.44,
            posterPath = "/8UlWHLMpgZm9bx6QYh0NFoq67TZ.jpg",
            productionCompanies = listOf(
                ProductionCompany(
                    id = 9993,
                    logoPath = "/2Tc1P3Ac8M479naPp1kYT3izLS5.png",
                    name = "DC Entertainment",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 174,
                    logoPath = "/IuAlhI9eVC9Z8UQWOIDdWRKSEJ.png",
                    name = "Warner Bros. Pictures",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 114152,
                    logoPath = null,
                    name = "The Stone Quarry",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 128064,
                    logoPath = "/13F3Jf7EFAcREU0xzZqJnVnyGXu.png",
                    name = "DC Films",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 507,
                    logoPath = "/z7H707qUWigbjHnJDMfj6QITEpb.png",
                    name = "Atlas Entertainment",
                    originCountry = "US"
                ),
                ProductionCompany(
                    id = 429,
                    logoPath = "/2Tc1P3Ac8M479naPp1kYT3izLS5.png",
                    name = "DC Comics",
                    originCountry = "US"
                )
            ),
            listOf(
                ProductionCountry(
                    iso31661 = "US",
                    name = "United States of America"
                ),
            ),
            releaseDate = "2020-12-16",
            revenue = 165160005,
            runtime = 151,
            spokenLanguages = listOf(
                SpokenLanguage(
                    englishName = "English",
                    iso6391 = "en",
                    name = "English"

                )
            ),
            status = "Released",
            tagline = "A new era of wonder begins.",
            title = "Wonder Woman 1984",
            video = false,
            voteAverage = 6.7,
            voteCount = 5349
        )
    }
}
