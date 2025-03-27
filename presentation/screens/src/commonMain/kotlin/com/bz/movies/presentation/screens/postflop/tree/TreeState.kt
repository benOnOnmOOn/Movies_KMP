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
    val betFlop: List<Int> = emptyList(),
    val betTurn: List<Int> = emptyList(),
    val betRiver: List<Int> = emptyList(),
    val raiseFlop: List<Int> = emptyList(),
    val raiseTurn: List<Int> = emptyList(),
    val raiseRiver: List<Int> = emptyList(),
    val donkTurn: List<Int> = emptyList(),
    val donkRiver: List<Int> = emptyList(),
)

internal sealed class TreeEditEvent {
    data class StartingPotUpdated(val startingPot: String) : TreeEditEvent()
    data class EffectiveStackUpdated(val effectiveStack: String) : TreeEditEvent()

    data class RakeUpdated(val rake: String) : TreeEditEvent()

    data class RakeCapUpdated(val rakeCap: String) : TreeEditEvent()

    data class DonksSwitched(val isEnabled: Boolean) : TreeEditEvent()
    data class AddAllInThresholdUpdated(val value: String) : TreeEditEvent()
    data class ForceAllInThresholdUpdated(val value: String) : TreeEditEvent()
    data class MergingThresholdUpdated(val value: String) : TreeEditEvent()

    data object Clear : TreeEditEvent()
}

internal sealed class TreeEditEffect {
    data object RangeParsingError : TreeEditEffect()
}
