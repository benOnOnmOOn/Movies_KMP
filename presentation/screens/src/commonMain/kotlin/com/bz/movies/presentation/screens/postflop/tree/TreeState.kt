package com.bz.movies.presentation.screens.postflop.tree

internal data class TreeState(
    val startingPot: Int = 20,
    val effectiveStack: Int = 100,
    val rake: Float = 0.0f,
    val rakeCap: Int = 0,
    val oopBetSize: BetSize = BetSize(),
    val isDonksEnabled: Boolean = false,
    val ipBetSize: BetSize = BetSize(),
    val allInThreshold: Int = 150,
    val forceAllInThreshold: Int = 20,
    val mergingThreshold: Int = 10,
)

internal data class BetSize(
    val berFlop: List<Int> = emptyList(),
    val betTurn: List<Int> = emptyList(),
    val betRiver: List<Int> = emptyList(),
    val donkTurn: List<Int> = emptyList(),
    val donkRiver: List<Int> = emptyList(),
)

internal sealed class TreeEditEvent {
    data class OnStartingPotUpdated(val startingPot: Int) : TreeEditEvent()

    data class OnDonksEnabled(val isEnabled: Boolean) : TreeEditEvent()

    data class OnRangeUpdated(val range: String) : TreeEditEvent()

    data class OnWeightUpdated(val weight: Float) : TreeEditEvent()

    data object Clear : TreeEditEvent()
}

internal sealed class TreeEditEffect {
    data object RangeParsingError : TreeEditEffect()
}
