package com.bz.movies.presentation.screens.postflop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bz.movies.presentation.screens.common.MovieEffect
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class PostflopRangeViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(RangeState())
    val state: StateFlow<RangeState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<RangeEditEvent> = MutableSharedFlow()
    private val event: SharedFlow<RangeEditEvent> = _event.asSharedFlow()

    private val _effect: Channel<MovieEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        handleEvent()
    }

    fun sendEvent(event: RangeEditEvent) =
        viewModelScope.launch {
            _event.emit(event)
        }

    private fun handleEvent() =
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }

    private suspend fun handleEvent(event: RangeEditEvent) {
        when (event) {
            RangeEditEvent.Clear -> _state.update { RangeState() }
            is RangeEditEvent.OnCardClicked -> _state.update {
                val newList = it.selectedHands.toMutableList()
                newList[event.handId] = !it.selectedHands[event.handId]
                val result: RangeState = it.copy(selectedHands = newList )
                result
            }
        }
    }

}
