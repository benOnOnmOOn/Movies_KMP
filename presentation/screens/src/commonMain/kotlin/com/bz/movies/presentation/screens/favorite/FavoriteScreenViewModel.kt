package com.bz.movies.presentation.screens.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.bz.movies.kmp.database.repository.LocalMovieRepository
import com.bz.movies.kmp.dto.MovieDto
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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class FavoriteScreenViewModel(
    private val localMovieRepository: LocalMovieRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<MovieEvent> = MutableSharedFlow()
    private val event: SharedFlow<MovieEvent> = _event.asSharedFlow()

    private val _effect: Channel<MovieEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        collectFavoriteMovies()
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
                localMovieRepository.deleteFavoriteMovie(event.movieItem.toDTO())

            MovieEvent.Refresh -> {
                // do nothing
            }
        }
    }

    private fun collectFavoriteMovies() {
        localMovieRepository.favoritesMovies
            .flowOn(Dispatchers.Main)
            .onEach { data ->
                _state.update {
                    MoviesState(
                        isLoading = false,
                        playingNowMovies = data.map(MovieDto::toMovieItem),
                    )
                }
            }
            .catch {
                _effect.send(MovieEffect.UnknownError)
                Logger.e(it.cause) { "Failed to fetch favorite movies from local db" }
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
