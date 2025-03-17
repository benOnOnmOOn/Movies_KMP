package com.bz.movies.presentation.screens.postflop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bz.movies.presentation.screens.common.MovieEffect
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class PostflopRangeViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(MoviesState())
    val state: StateFlow<MoviesState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<MovieEvent> = MutableSharedFlow()
    private val event: SharedFlow<MovieEvent> = _event.asSharedFlow()

    private val _effect: Channel<MovieEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
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
        // TODO:  Add this later
    }

}
