package com.bz.movies.presentation.screens.postflop.tree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bz.movies.presentation.screens.postflop.range.RangeEffect
import com.bz.movies.presentation.screens.postflop.utils.Range
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

internal class PostflopTreViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(TreeState())
    val state: StateFlow<TreeState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<TreeEditEvent> = MutableSharedFlow()
    private val event: SharedFlow<TreeEditEvent> = _event.asSharedFlow()

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

    fun sendEvent(event: TreeEditEvent) =
        viewModelScope.launch {
            _event.emit(event)
        }

    private fun handleEvent() =
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }

    private suspend fun handleEvent(event: TreeEditEvent) {
        when (event) {
            is TreeEditEvent.AllInThresholdUpdated -> {
                event.value.toIntOrNull()?.let { allInThreshold ->
                    _state.update { _state.value.copy(allInThreshold = allInThreshold) }
                }
            }

            TreeEditEvent.Clear -> _state.update { TreeState() }

            is TreeEditEvent.DonksSwitched -> {
                _state.update {
                    _state.value.copy(isDonksEnabled = event.isEnabled)
                }
            }

            is TreeEditEvent.EffectiveStackUpdated -> {
                event.effectiveStack.toIntOrNull()?.let { effectiveStack ->
                    _state.update { _state.value.copy(effectiveStack = effectiveStack) }
                }
            }

            is TreeEditEvent.ForceAllInThresholdUpdated -> {
                event.value.toIntOrNull()?.let { forceAllInThreshold ->
                    _state.update { _state.value.copy(forceAllInThreshold = forceAllInThreshold) }
                }
            }

            is TreeEditEvent.MergingThresholdUpdated -> {
                event.value.toIntOrNull()?.let { mergingThreshold ->
                    _state.update { _state.value.copy(mergingThreshold = mergingThreshold) }
                }
            }

            is TreeEditEvent.RakeCapUpdated -> {
                event.rakeCap.toIntOrNull()?.let { rakeCap ->
                    _state.update { _state.value.copy(rakeCap = rakeCap) }
                }
            }

            is TreeEditEvent.RakeUpdated -> {
                event.rake.toFloatOrNull()?.let { rake ->
                    _state.update { _state.value.copy(rake = rake) }
                }
            }

            is TreeEditEvent.StartingPotUpdated -> {
                event.startingPot.toIntOrNull()?.let { startingPot ->
                    _state.update { _state.value.copy(startingPot = startingPot) }
                }
            }

            is TreeEditEvent.BetUpdated -> {
                val newBets = event.value.split(",").mapNotNull { bet -> bet.toIntOrNull() }
                _state.update {
                    when (event.betType) {
                        BetType.OOP -> {
                            when (event.streetType) {
                                StreetType.FLOP -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(betFlop = newBets))
                                }

                                StreetType.TURN -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(betTurn = newBets))
                                }

                                StreetType.RIVER -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(betRiver = newBets))
                                }
                            }
                        }

                        BetType.IP -> {
                            when (event.streetType) {
                                StreetType.FLOP -> {
                                    it.copy(ipBetSize = it.ipBetSize.copy(betFlop = newBets))
                                }

                                StreetType.TURN -> {
                                    it.copy(ipBetSize = it.ipBetSize.copy(betTurn = newBets))
                                }

                                StreetType.RIVER -> {
                                    it.copy(ipBetSize = it.ipBetSize.copy(betRiver = newBets))
                                }
                            }
                        }
                    }
                }
            }

            is TreeEditEvent.DonkUpdated -> {
                val newBets = event.value.split(",").mapNotNull { bet -> bet.toIntOrNull() }
                _state.update {
                    when (event.betType) {
                        BetType.OOP -> {
                            when (event.streetType) {
                                StreetType.FLOP -> error("Donk's not allowed in FLOP")
                                StreetType.TURN -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(donkTurn = newBets))
                                }

                                StreetType.RIVER -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(donkRiver = newBets))
                                }
                            }
                        }

                        BetType.IP -> {
                            error("Donk bet can be made only OOP")
                        }
                    }
                }
            }

            is TreeEditEvent.RaiseUpdated -> {
                val newBets = event.value.split(",").mapNotNull { bet -> bet.toIntOrNull() }
                _state.update {
                    when (event.betType) {
                        BetType.OOP -> {
                            when (event.streetType) {
                                StreetType.FLOP -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(raiseFlop = newBets))
                                }

                                StreetType.TURN -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(raiseTurn = newBets))
                                }

                                StreetType.RIVER -> {
                                    it.copy(oopBetSize = it.oopBetSize.copy(raiseRiver = newBets))
                                }
                            }
                        }

                        BetType.IP -> {
                            when (event.streetType) {
                                StreetType.FLOP -> {
                                    it.copy(ipBetSize = it.ipBetSize.copy(raiseFlop = newBets))
                                }

                                StreetType.TURN -> {
                                    it.copy(ipBetSize = it.ipBetSize.copy(raiseTurn = newBets))
                                }

                                StreetType.RIVER -> {
                                    it.copy(ipBetSize = it.ipBetSize.copy(raiseRiver = newBets))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
