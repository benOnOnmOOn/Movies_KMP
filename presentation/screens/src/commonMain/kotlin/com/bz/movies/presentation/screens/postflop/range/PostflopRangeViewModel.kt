package com.bz.movies.presentation.screens.postflop.range

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bz.movies.presentation.screens.postflop.utils.Range
import com.bz.movies.presentation.screens.postflop.utils.getWeightOffsuit
import com.bz.movies.presentation.screens.postflop.utils.getWeightPair
import com.bz.movies.presentation.screens.postflop.utils.getWeightSuited
import com.bz.movies.presentation.screens.postflop.utils.setWeightOffsuit
import com.bz.movies.presentation.screens.postflop.utils.setWeightPair
import com.bz.movies.presentation.screens.postflop.utils.setWeightSuited
import com.bz.movies.presentation.screens.postflop.utils.toRange
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

    /**
     * Combinations range for all possible card combination.
     * We are displaying only card singletons data,
     * and reuse all of this data when updating any weight in range
     */
    private var range: Range = Range()

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
                        range = it
                        updateRangeState()
                    }
                    .onFailure {
                        _state.update {
                            _state.value.copy(inputError = true)
                        }
                    }

            }

            is RangeEditEvent.OnWeightUpdated -> {
                _state.update {
                    _state.value.copy(
                        inputWeight = event.weight.coerceIn(0.0f, 1.0f).roundToDecimals(2)
                    )
                }
            }
        }
    }

    fun updateRangeState() {
        val combination = range.data.sum()
        val percent: Float = combination / range.data.size * 100
        val dec = percent.roundToDecimals(2)

        _state.update {
            _state.value.copy(
                weights = range.toUiWeight(),
                combinations = combination,
                compinationPercent = dec,
                inputRange = range.toString(),
                inputError = false
            )
        }
    }

    private fun onCardClicked(event: RangeEditEvent.OnCardClicked) {
        val firstRank = 12 - event.handId % 13
        val secondRank = 12 - event.handId / 13
        val weight = state.value.inputWeight
        if (firstRank == secondRank) {
            val currentWeight = range.getWeightPair(firstRank)
            val newWeight = if (currentWeight.eq(weight, 0.01f)) 0.0f else weight
            range.setWeightPair(firstRank, newWeight)
        } else if (firstRank > secondRank) {
            val currentWeight = range.getWeightOffsuit(firstRank, secondRank)
            val newWeight = if (currentWeight.eq(weight, 0.01f)) 0.0f else weight
            range.setWeightOffsuit(firstRank, secondRank, newWeight)
        } else {
            val currentWeight = range.getWeightSuited(firstRank, secondRank)
            val newWeight = if (currentWeight.eq(weight, 0.01f)) 0.0f else weight
            range.setWeightSuited(firstRank, secondRank, newWeight)
        }

        updateRangeState()
    }
}
