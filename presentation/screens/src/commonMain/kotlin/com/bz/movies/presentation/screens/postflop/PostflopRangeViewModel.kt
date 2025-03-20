package com.bz.movies.presentation.screens.postflop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bz.movies.presentation.utils.eq
import com.bz.movies.presentation.utils.roundToDecimals
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
                    _state.value.copy(weight = event.weight.coerceIn(0.0f, 1.0f).roundToDecimals(2))
                }
            }
        }
    }

    private fun onCardClicked(event: RangeEditEvent.OnCardClicked) {
        val firstRank = event.firstRank
        val secondRank = event.secondRank
        val newRange = Range(state.value.range.data.copyOf())
        val weight = state.value.weight
        if (firstRank == secondRank) {
            val currentWeight = state.value.range.getWeightPair(firstRank)
            if (currentWeight.eq(weight, 0.01f)) {
                newRange.setWeightPair(firstRank, 0.0f)
            } else {
                newRange.setWeightPair(firstRank, weight)
            }
        } else if (firstRank > secondRank) {
            val currentWeight = state.value.range.getWeightOffsuit(firstRank, secondRank)
            if (currentWeight.eq(weight, 0.01f)) {
                newRange.setWeightOffsuit(firstRank, secondRank, 0.0f)
            } else {
                newRange.setWeightOffsuit(firstRank, secondRank, weight)
            }
        } else {
            val currentWeight = state.value.range.getWeightSuited(firstRank, secondRank)
            if (currentWeight.eq(weight, 0.01f)) {
                newRange.setWeightSuited(firstRank, secondRank, 0.0f)
            } else {
                newRange.setWeightSuited(firstRank, secondRank, weight)
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
