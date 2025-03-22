package com.bz.movies.presentation.screens.postflop.range

import com.bz.movies.presentation.screens.postflop.utils.Range
import com.bz.movies.presentation.screens.postflop.utils.getWeightOffsuit
import com.bz.movies.presentation.screens.postflop.utils.getWeightPair
import com.bz.movies.presentation.screens.postflop.utils.getWeightSuited
import com.bz.movies.presentation.screens.postflop.utils.rankToChar

internal const val RANGE_BOARD_SIZE = 169
internal data class RangeState(
    val weights: FloatArray = FloatArray(RANGE_BOARD_SIZE) { 0f },
    val cardsSingleton: List<String> = List(RANGE_BOARD_SIZE) { it.toCardSingleton() },
    val combinations: Float = 0.0f,
    val compinationPercent: Float = 0.0f,
    val inputRange: String = "",
    val inputWeight: Float = 1f,
    val inputError: Boolean = false,
)

internal sealed class RangeEditEvent {
    data class OnCardClicked(val handId: Int) : RangeEditEvent()

    data class OnRangeUpdated(val range: String) : RangeEditEvent()

    data class OnWeightUpdated(val weight: Float) : RangeEditEvent()

    data object Clear : RangeEditEvent()
}


internal sealed class RangeEffect {
    data object RangeParsingError : RangeEffect()
}

internal fun Int.toCardSingleton(): String {
    val firstRank = 12 - this % 13
    val secondRank = 12 - this / 13
    val firstRankChar = rankToChar(firstRank)
    val secondRankChar = rankToChar(secondRank)

    return if (firstRank == secondRank) {
        "$firstRankChar$secondRankChar"
    } else if (firstRank > secondRank) {
        "$firstRankChar${secondRankChar}o"
    } else {
        "$secondRankChar${firstRankChar}s"
    }
}

internal fun Range.toUiWeight(): FloatArray {
    return FloatArray(RANGE_BOARD_SIZE) {
        val firstRank = 12 - it % 13
        val secondRank = 12 - it / 13

        if (firstRank == secondRank) {
            getWeightPair(firstRank)
        } else if (firstRank > secondRank) {
            getWeightOffsuit(firstRank, secondRank)
        } else {
            getWeightSuited(firstRank, secondRank)
        }
    }
}

