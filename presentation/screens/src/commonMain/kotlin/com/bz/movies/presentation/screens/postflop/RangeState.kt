package com.bz.movies.presentation.screens.postflop

internal data class RangeState(
    val range: String = "",
    val hands: List<Pair<CardUI, CardUI>> = emptyList(),
    val selectedHands: List<Boolean> = List(169) { false }
)

internal sealed class RangeEditEvent {
    data class OnCardClicked(val handId: Int) : RangeEditEvent()

    data class OnRangeUpdated(val range: String) : RangeEditEvent()

    data object Clear : RangeEditEvent()
}


internal data class CardUI(val rank: Int, val suite: Char)

internal const val RANKS = "23456789TJQKA"

internal const val SUITS = "23456789TJQKA"