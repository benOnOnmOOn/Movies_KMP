package com.bz.movies.presentation.screens.postflop

internal data class RangeState(
    val range: String = "",
    val hands: List<Pair<Card, Card>> = emptyList(),
    val selectedHands: List<Boolean> = List(169) { false }
)

internal sealed class RangeEditEvent {
    data class OnCardClicked(val handId: Int) : RangeEditEvent()

    data class OnRangeUpdated(val range: String) : RangeEditEvent()

    data object Clear : RangeEditEvent()
}


internal data class Card(val rank: Int, val suite: Char)

internal const val RANKS = "23456789TJQKA"