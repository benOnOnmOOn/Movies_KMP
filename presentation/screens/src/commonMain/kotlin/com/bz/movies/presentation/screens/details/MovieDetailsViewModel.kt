package com.bz.movies.presentation.screens.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.bz.movies.kmp.network.repository.MovieRepository
import com.bz.movies.kmp.network.repository.NoInternetException
import com.bz.movies.presentation.screens.common.MovieDetailState
import com.bz.movies.presentation.screens.common.MovieEffect
import com.bz.movies.presentation.screens.common.MovieItem
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.random.Random

internal class MovieDetailsViewModel(private val movieRepository: MovieRepository) : ViewModel() {
    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    private val _effect: MutableSharedFlow<MovieEffect> = MutableSharedFlow()
    val effect = _effect.asSharedFlow()

    @Suppress("MagicNumber")
    fun fetchMovieDetails(movieId: Int) {
        viewModelScope.launch {
            val result = movieRepository.getMovieDetail(movieId)
            result.onSuccess { data ->
                _state.update {
                    MovieDetailState(
                        isLoading = false,
                        movieDetails =
                            MovieItem(
                                id = movieId,
                                language = data.language,
                                posterUrl = data.posterUrl,
                                title = data.title,
                                rating = Random.nextInt(40, 90),
                                releaseDate = data.publicationDate,
                            ),
                    )
                }
            }

            result.onFailure {
                val error =
                    when (it) {
                        is NoInternetException -> {
                            Logger.w(it) { "Failed to fetch movie details. Network exception" }
                            MovieEffect.NetworkConnectionError
                        }
                        else -> {
                            Logger.e(it) { "Failed to fetch movie details" }
                            MovieEffect.UnknownError
                        }
                    }
                _effect.emit(error)

                _state.update { MovieDetailState(isLoading = false) }
            }
        }
    }
}
