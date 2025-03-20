package com.bz.movies.presentation.screens.postflop

internal data class RangeState(
    val range: Range = Range(),
    val inputRange: String = "",
    val inputError: Boolean = false,
)

internal sealed class RangeEditEvent {
    data class OnCardClicked(val firstRank: Int, val secondRank: Int) : RangeEditEvent()

    data class OnRangeUpdated(val range: String) : RangeEditEvent()

    data object Clear : RangeEditEvent()
}


internal sealed class RangeEffect {
    data object RangeParsingError : RangeEffect()
}


internal const val RANKS = "23456789TJQKA"

