package com.bz.movies.presentation.screens.playingNow

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
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

internal class PlayingNowViewModel(
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
        collectPlayingNowMovies()
        handleEvent()
    }

    fun sendEvent(event: MovieEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    private fun handleEvent() {
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }
    }

    private suspend fun handleEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.OnMovieClicked ->
                localMovieRepository.insertFavoriteMovie(event.movieItem.toDTO())

            MovieEvent.Refresh -> {
                _state.update {
                    MoviesState(
                        isLoading = false,
                        isRefreshing = true,
                        playingNowMovies = emptyList(),
                    )
                }
                fetchPlayingNowMovies()
            }
        }
    }

    private fun fetchPlayingNowMovies() {
        viewModelScope.launch {
            localMovieRepository.clearPlayingNowMovies()
            val result = movieRepository.getPlayingNowMovies()

            result.onSuccess { data ->
                dataStoreRepository.insertPlayingNowRefreshDate(Clock.System.now())
                localMovieRepository.insertPlayingNowMovies(data)
            }
            result.onFailure {
                val error =
                    when (it) {
                        is NoInternetException -> {
                            Logger.w(it.cause) { "Failed to fetch playing now movies. Network exception" }
                            MovieEffect.NetworkConnectionError
                        }

                        else -> {
                            Logger.e(it) { "Failed to fetch playing now movies" }
                            MovieEffect.UnknownError
                        }
                    }
                _effect.send(error)
            }
        }
    }

    private fun collectPlayingNowMovies() {
        localMovieRepository.playingNowMovies
            .flowOn(Dispatchers.Main)
            .onEmpty { fetchPlayingNowMovies() }
            .onEach { data ->
                val lastDate = dataStoreRepository.getPlyingNowRefreshDate()
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
