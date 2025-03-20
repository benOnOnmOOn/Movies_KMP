package com.bz.movies.presentation.screens.postflop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _effect: Channel<RangeEffect> = Channel()
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
            is RangeEditEvent.OnCardClicked -> onCardClicked(event)

            is RangeEditEvent.OnRangeUpdated -> {
                _state.update {
                    _state.value.copy(inputRange = event.range)
                }
                runCatching { event.range.toRange() }
                    .onSuccess {
                        _state.update {
                            _state.value.copy(range = event.range.toRange(), inputError = false)
                        }
                    }
                    .onFailure {
                        _state.update {
                            _state.value.copy(inputError = true)
                        }
                    }

            }

            is RangeEditEvent.OnWeightUpdated -> {
                _state.update {
                    _state.value.copy(weight = event.weight)
                }
            }
        }
    }

    private fun onCardClicked(event: RangeEditEvent.OnCardClicked) {
        val firstRank = event.firstRank
        val secondRank = event.secondRank
        val newRange = Range(state.value.range.data.copyOf())
        if (firstRank == secondRank) {
            val currentWeight = state.value.range.getWeightPair(firstRank)
            if (currentWeight > 0.001f) {
                newRange.setWeightPair(firstRank, 0.0f)
            } else {
                newRange.setWeightPair(firstRank, 1.0f)
            }
        } else if (firstRank > secondRank) {
            val currentWeight = state.value.range.getWeightOffsuit(firstRank, secondRank)
            if (currentWeight > 0.001f) {
                newRange.setWeightOffsuit(firstRank, secondRank, 0.0f)
            } else {
                newRange.setWeightOffsuit(firstRank, secondRank, 1.0f)
            }
        } else {
            val currentWeight = state.value.range.getWeightSuited(firstRank, secondRank)
            if (currentWeight > 0.001f) {
                newRange.setWeightSuited(firstRank, secondRank, 0.0f)
            } else {
                newRange.setWeightSuited(firstRank, secondRank, 1.0f)
            }
        }

        _state.update {
            _state.value.copy(
                range = newRange,
                inputRange = newRange.toString(),
                inputError = false
            )
        }
    }
}
