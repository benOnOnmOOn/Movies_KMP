package com.bz.movies.presentation.screens.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.bz.movies.kmp.database.repository.LocalMovieRepository
import com.bz.movies.kmp.datastore.repository.DataStoreRepository
import com.bz.movies.kmp.dto.MovieDto
import com.bz.movies.kmp.network.repository.MovieRepository
import com.bz.movies.kmp.network.repository.NoInternetException
import com.bz.movies.presentation.mappers.toDTO
import com.bz.movies.presentation.mappers.toMovieItem
import com.bz.movies.presentation.screens.common.MovieEffect
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

internal class PopularMoviesViewModel(
    private val movieRepository: MovieRepository,
    private val localMovieRepository: LocalMovieRepository,
    private val dataStoreRepository: DataStoreRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<MovieEvent> = MutableSharedFlow()
    private val event: SharedFlow<MovieEvent> = _event.asSharedFlow()

    private val _effect: Channel<MovieEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        collectPopularMovies()
        handleEvent()
    }

    fun sendEvent(event: MovieEvent) =
        viewModelScope.launch {
            _event.emit(event)
        }

    private fun handleEvent() =
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }

    private suspend fun handleEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.OnMovieClicked ->
                localMovieRepository.insertFavoriteMovie(event.movieItem.toDTO())

            MovieEvent.Refresh -> {
                localMovieRepository.clearPopularMovies()
                fetchPopularNowMovies()
            }
        }
    }

    private fun fetchPopularNowMovies() {
        viewModelScope.launch {
            val result = movieRepository.getPopularMovies(1)

            result.onSuccess { data ->
                dataStoreRepository.insertPopularNowRefreshDate(Clock.System.now())
                    .onFailure { Logger.e(it) { "Failed to insert Popular Now Refresh Date" } }
                _state.update {
                    MoviesState(
                        isLoading = false,
                        playingNowMovies = data.map(MovieDto::toMovieItem),
                    )
                }
                localMovieRepository.insertPopularMovies(data)
            }
            result.onFailure {
                val error =
                    when (it) {
                        is NoInternetException -> {
                            Logger.w(it) { "Failed to fetch popular movies. Network exception" }
                            MovieEffect.NetworkConnectionError
                        }

                        else -> {
                            Logger.e(it) { "Failed to fetch popular movies" }
                            MovieEffect.UnknownError
                        }
                    }
                _effect.send(error)
                _state.update { MoviesState(isLoading = false) }
            }
        }
    }

    private fun collectPopularMovies() {
        localMovieRepository.popularMovies
            .flowOn(Dispatchers.Main)
            .onStart { fetchPopularNowMovies() }
            .onEach { data ->
                val lastDate =
                    dataStoreRepository.getPopularRefreshDate()
                        .onFailure { Logger.e(it) { "Failed to get Popular Now Refresh Date" } }
                Logger.d("Last date : $lastDate}")
                _state.update {
                    MoviesState(
                        isLoading = false,
                        playingNowMovies = data.map(MovieDto::toMovieItem),
                    )
                }
            }
            .catch {
                _effect.send(MovieEffect.UnknownError)
                _state.update {
                    MoviesState(
                        isLoading = false,
                        isRefreshing = false,
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}
