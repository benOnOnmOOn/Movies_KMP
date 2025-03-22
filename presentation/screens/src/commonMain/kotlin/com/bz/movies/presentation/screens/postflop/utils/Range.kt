package com.bz.movies.presentation.screens.postflop.utils

import kotlin.math.ceil
import kotlin.math.sqrt

internal data class Range(
    val data: FloatArray = FloatArray(52 * 51 / 2)
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Range

        return data.contentEquals(other.data)
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }

    /**
     * Converts the Range object to a string representation.
     *
     * @return The string representation of the range.
     */
    override fun toString(): String {
        val result = mutableListOf<String>()
        pairsStrings(result)
        nonPairsStrings(result)
        suitSpecifiedStrings(result)
        return result.joinToString(",")
    }

}

typealias Card = Int

internal sealed class Suitedness {
    data object Suited : Suitedness()
    data object Offsuit : Suitedness()
    data object All : Suitedness()
    data class Specific(val first: Int, val second: Int) : Suitedness()
}

internal const val COMBO_PAT = "(?:(?:[AaKkQqJjTt2-9]{2}[os]?)|(?:(?:[AaKkQqJjTt2-9][cdhs]){2}))"

internal const val WEIGHT_PAT = "(?:(?:[01](\\.\\d*)?)|(?:\\.\\d+))"

internal const val RANGE_REGEX =
    "^(?<range>$COMBO_PAT(?:\\+|(-$COMBO_PAT))?)(?::(?<weight>$WEIGHT_PAT))?$"

internal fun pairIndices(rank: Int): List<Int> {
    return buildList(6) {
        for (i in 0..3) {
            for (j in i + 1..3) {
                add(cardPairToIndex(4 * rank + i, 4 * rank + j))
            }
        }
    }
}

internal fun nonPairIndices(rank1: Int, rank2: Int): List<Int> {
    return buildList(16) {
        for (i in 0..3) {
            for (j in 0..3) {
                add(cardPairToIndex(4 * rank1 + i, 4 * rank2 + j))
            }
        }
    }
}

internal fun suitedIndices(rank1: Int, rank2: Int): List<Int> {
    return buildList {
        for (i in 0..3) {
            add(cardPairToIndex(4 * rank1 + i, 4 * rank2 + i))
        }
    }
}

internal fun offsuitIndices(rank1: Int, rank2: Int): List<Int> {
    return buildList(12) {
        for (i in 0..3) {
            for (j in 0..3) {
                if (i != j) {
                    add(cardPairToIndex(4 * rank1 + i, 4 * rank2 + j))
                }
            }
        }
    }
}

internal fun indicesWithSuitedness(rank1: Int, rank2: Int, suitedness: Suitedness): List<Int> {
    return if (rank1 == rank2) {
        when (suitedness) {
            Suitedness.All -> pairIndices(rank1)
            is Suitedness.Specific -> listOf(
                cardPairToIndex(4 * rank1 + suitedness.first, 4 * rank1 + suitedness.second)
            )

            else -> error("Invalid suitedness with a pair")
        }
    } else {
        when (suitedness) {
            Suitedness.All -> nonPairIndices(rank1, rank2)
            Suitedness.Offsuit -> offsuitIndices(rank1, rank2)
            is Suitedness.Specific ->
                listOf(
                    cardPairToIndex(4 * rank1 + suitedness.first, 4 * rank2 + suitedness.second)
                )

            Suitedness.Suited -> suitedIndices(rank1, rank2)
        }
    }
}

internal fun charToRank(c: Char): Int {
    return when (c) {
        'A', 'a' -> 12
        'K', 'k' -> 11
        'Q', 'q' -> 10
        'J', 'j' -> 9
        'T', 't' -> 8
        in '2'..'9' -> c - '2'
        else -> error("Expected rank character: $c")
    }
}

internal fun charToSuit(c: Char): Int {
    return when (c) {
        'c' -> 0
        'd' -> 1
        'h' -> 2
        's' -> 3
        else -> error("Expected suit character: $c")
    }
}

internal fun rankToChar(rank: Int): Char {
    return when (rank) {
        12 -> 'A'
        11 -> 'K'
        10 -> 'Q'
        9 -> 'J'
        8 -> 'T'
        in 0..7 -> ('2' + rank)
        else -> error("Invalid input: $rank ")
    }
}

internal fun suitToChar(suit: Int): Char {
    return when (suit) {
        0 -> 'c'
        1 -> 'd'
        2 -> 'h'
        3 -> 's'
        else -> error("Invalid input: $suit")
    }
}

internal fun cardPairToIndex(card1: Card, card2: Card): Int {
    return if (card1 > card2) {
        card2 * (101 - card2) / 2 + card1 - 1
    } else {
        card1 * (101 - card1) / 2 + card2 - 1
    }
}

internal fun indexToCardPair(index: Int): Pair<Card, Card> {
    val card1 = (103 - ceil(sqrt(103.0 * 103.0 - 8.0 * index)).toInt()) / 2
    val card2 = index - card1 * (101 - card1) / 2 + 1
    return Pair(card1, card2)
}

/**
 * Updates the range with a dash-separated range (e.g., "88-55", "AKs-AQs").
 *
 * @param range The dash-separated range string.
 * @param weight The weight to apply to the combinations in the range.
 */
private fun Range.updateWithDashRange(range: String, weight: Float) {
    val comboPair = range.split('-')
    check(comboPair.size == 2) { "Invalid range format: $range" }

    val (rank11, rank12, suitedness) = parseSingleton(comboPair[0])
    val (rank21, rank22, suitedness2) = parseSingleton(comboPair[1])

    val gap = rank11 - rank12
    val gap2 = rank21 - rank22

    if (suitedness != suitedness2) {
        error("Suitedness does not match: $range")
    } else if (gap == gap2) {
        // Same gap (e.g., 88-55, KQo-JTo)
        if (rank11 > rank21) {
            for (i in rank21..rank11) {
                setWeight(indicesWithSuitedness(i, i - gap, suitedness), weight)
            }
        } else {
            error("Range must be in descending order: $range")
        }
    } else if (rank11 == rank21) {
        // Same first rank (e.g., A5s-A2s)
        if (rank12 > rank22) {
            for (i in rank22..rank12) {
                setWeight(indicesWithSuitedness(rank11, i, suitedness), weight)
            }
        } else {
            error("Range must be in descending order: $range")
        }
    } else {
        error("Invalid range: $range")
    }
}

/**
 * Updates the range with a plus-separated range (e.g., "88+", "T9s+", "ATo+").
 *
 * @param range The plus-separated range string.
 * @param weight The weight to apply to the combinations in the range.
 */
private fun Range.updateWithPlusRange(range: String, weight: Float) {
    val lowestCombo = range.substring(0, range.length - 1)
    val (rank1, rank2, suitedness) = parseSingleton(lowestCombo)
    val gap = rank1 - rank2

    if (gap <= 1) {
        // Pair and connector (e.g., 88+, T9s+)
        for (i in rank1..12) {
            setWeight(indicesWithSuitedness(i, i - gap, suitedness), weight)
        }
    } else {
        // Otherwise (e.g., ATo+)
        for (i in rank2..<rank1) {
            setWeight(indicesWithSuitedness(rank1, i, suitedness), weight)
        }
    }
}

/**
 * Updates the range with a single combination (e.g., "8s", "KQo", "AA").
 *
 * @param combo The single combination string.
 * @param weight The weight to apply to the combination.
 */
private fun Range.updateWithSingleton(combo: String, weight: Float) {
    val (rank1, rank2, suitedness) = parseSingleton(combo)
    setWeight(indicesWithSuitedness(rank1, rank2, suitedness), weight)
}

/**
 * Sets the weight for a list of indices in the range's data array.
 *
 * @param indices The list of indices to update.
 * @param weight The weight to set for the indices.
 */
private fun Range.setWeight(indices: List<Int>, weight: Float) {
    for (index in indices) {
        data[index] = weight
    }
}

/**
 * Parses a single combination string (e.g., "8s", "KQo", "AA").
 *
 * @param combo The combination string to parse.
 * @return A Triple containing the first rank, second rank, and suitedness.
 */
private fun parseSingleton(combo: String): Triple<Int, Int, Suitedness> {
    return if (combo.length == 4) {
        parseSimpleSingleton(combo)
    } else {
        parseCompoundSingleton(combo)
    }
}

/**
 * Parses a simple combination string (e.g., "AcKh").
 *
 * @param combo The combination string to parse.
 * @return A Triple containing the first rank, second rank, and suitedness.
 * @throws IllegalStateException if the combo string is not in a valid format.
 */
private fun parseSimpleSingleton(combo: String): Triple<Int, Int, Suitedness> {
    require(combo.length == 4) {
        "Expected simple combo of 4 characters, got ${combo.length}: $combo"
    }

    val rank1 = charToRank(combo[0])
    val suit1 = charToSuit(combo[1])
    val rank2 = charToRank(combo[2])
    val suit2 = charToSuit(combo[3])

    require(rank1 >= rank2) {
        "The first rank must be equal or higher than the second rank: $combo"
    }
    require(rank1 != rank2 || suit1 != suit2) {
        "Duplicate cards are not allowed: $combo"
    }
    return Triple(rank1, rank2, Suitedness.Specific(suit1, suit2))
}

/**
 * Parses a compound combination string (e.g., "8s", "KQo", "AA").
 *
 * @param combo The combination string to parse.
 * @return A Triple containing the first
 *
rank, second rank, and suitedness.
 * @throws IllegalStateException if the combo string is not in a valid format.
 */
private fun parseCompoundSingleton(combo: String): Triple<Int, Int, Suitedness> {
    check(combo.length in 2..3) {
        "Expected compound combo of 2-3 characters, got ${combo.length}: $combo"
    }

    val rank1 = charToRank(combo[0])
    val rank2 = charToRank(combo[1])
    val suitedness = if (combo.length == 2) {
        Suitedness.All
    } else {
        when (combo[2]) {
            's' -> Suitedness.Suited
            'o' -> Suitedness.Offsuit
            else -> throw IllegalStateException("Invalid suitedness: $combo")
        }
    }

    check(rank1 >= rank2) {
        "The first rank must be equal or higher than the second rank: $combo"
    }
    check(rank1 != rank2 || suitedness == Suitedness.All) {
        "A pair with suitedness is not allowed: $combo"
    }
    return Triple(rank1, rank2, suitedness)
}

/**
 * Creates a Range object from a string representation.
 *
 * @return A new Range object populated from the string.
 * @throws IllegalStateException if the string is not a valid range.
 */
internal fun String.toRange(): Range {
    val ranges = filter { !it.isWhitespace() }
        .split(",")
        .filter { it.isNotBlank() }
        .reversed()

    val result = Range()

    for (rangeStr in ranges) {
        val matchResult = RANGE_REGEX.toRegex().find(rangeStr)
        if (matchResult == null) {
            error("Failed to parse range: $rangeStr")
        }

        val range = matchResult.groups["range"]?.value ?: error("Failed to parse range: $rangeStr")
        val weight = matchResult.groups["weight"]?.value?.toFloatOrNull() ?: 1.0f
        checkWeight(weight)

        when {
            range.contains('-') -> result.updateWithDashRange(range, weight)
            range.contains('+') -> result.updateWithPlusRange(range, weight)
            else -> result.updateWithSingleton(range, weight)
        }
    }

    return result
}

/**
 * Checks if a given weight is within the valid range [0.0, 1.0].
 *
 * @param weight The weight to check.
 * @throws IllegalStateException if the weight is not in the valid range.
 */
private fun checkWeight(weight: Float) {
    if (weight < 0.0 || weight > 1.0) {
        error("Invalid weight: $weight")
    }
}

/**
 * Calculates the average weight for a list of indices in the range's data array.
 *
 * @param indices The list of indices to calculate the average weight for.
 * @return The average weight of the specified indices.
 */
private fun Range.getAverageWeight(indices: List<Int>): Float {
    var sum = 0.0f
    for (i in indices) {
        sum += data[i]
    }
    return sum / indices.size.toFloat()
}

/**
 * Obtains the weight of a specified hand.
 *
 * @param card1 The first card (0-51).
 * @param card2 The second card (0-51).
 * @return The weight of the specified hand.
 * @throws IllegalArgumentException if `card1` or `card2` is not less than 52
 * or `card1` is equal to `card2`.
 */
internal fun Range.getWeightByCards(card1: Card, card2: Card): Float {
    require(card1 in 0..51 && card2 in 0..51) {
        "card1 and card2 must be between 0 and 51"
    }
    require(card1 != card2) { "card1 and card2 must be different" }
    return data[cardPairToIndex(card1, card2)]
}

/**
 * Obtains the average weight of specified pair hands.
 *
 * @param rank The rank of the pair (0-12).
 * @return The average weight of the specified pair hands.
 * @throws IllegalArgumentException if `rank` is not less than 13.
 */
internal fun Range.getWeightPair(rank: Int): Float {
    require(rank in 0..12) { "rank must be between 0 and 12" }
    return getAverageWeight(pairIndices(rank))
}

/**
 * Obtains the average weight of specified suited hands.
 *
 * @param rank1 The first rank (0-12).
 * @param rank2 The second rank (0-12).
 * @return The average weight of the specified suited hands.
 * @throws IllegalArgumentException if `rank1` or `rank2` is not less than 13
 * or `rank1` is equal to `rank2`.
 */
internal fun Range.getWeightSuited(rank1: Int, rank2: Int): Float {
    require(rank1 in 0..12 && rank2 in 0..12) {
        "rank1 and rank2 must be between 0 and 12"
    }
    require(rank1 != rank2) { "rank1 and rank2 must be different" }
    return getAverageWeight(suitedIndices(rank1, rank2))
}

/**
 * Obtains the average weight of specified offsuit hands.
 *
 * @param rank1 The first rank (0-12).
 * @param rank2 The second rank (0-12).
 * @return The average weight of the specified offsuit hands.
 * @throws IllegalArgumentException if `rank1` or `rank2` is not less than 13
 * or `rank1` is equal to `rank2`.
 */
internal fun Range.getWeightOffsuit(rank1: Int, rank2: Int): Float {
    check(rank1 in 0..12 && rank2 in 0..12) {
        "rank1 and rank2 must be between 0 and 12"
    }
    require(rank1 != rank2) { "rank1 and rank2 must be different" }
    return getAverageWeight(offsuitIndices(rank1, rank2))
}

/**
 * Sets the weight of a specified hand.
 *
 * @param card1 The first card (0-51).
 * @param card2 The second card (0-51).
 * @param weight The weight to set (0.0-1.0).
 * @throws IllegalArgumentException if `card1` or `card2` is not less than 52
 * or `card1` is equal to `card2` or `weight` is not in the range `[0.0, 1.0]`.
 */
internal fun Range.setWeightByCards(card1: Int, card2: Int, weight: Float) {
    check(card1 in 0..51 && card2 in 0..51) {
        "card1 and card2 must be between 0 and 51"
    }
    require(card1 != card2) { "card1 and card2 must be different" }
    require(weight in 0.0..1.0) { "weight must be between 0.0 and 1.0" }
    data[cardPairToIndex(card1, card2)] = weight
}


/**
 * Sets the weights of specified pair hands.
 *
 * @param rank The rank of the pair (0-12).
 * @param weight The weight to set (0.0-1.0).
 * @throws IllegalArgumentException if `rank` is not less than 13
 * or `weight` is not in the range `[0.0, 1.0]`.
 */
internal fun Range.setWeightPair(rank: Int, weight: Float) {
    require(rank in 0..12) { "rank must be between 0 and 12" }
    require(weight in 0.0..1.0) { "weight must be between 0.0 and 1.0" }
    setWeight(pairIndices(rank), weight)
}

/**
 * Sets the weights of specified suited hands.
 *
 * @param rank1 The first rank (0-12).
 * @param rank2 The second rank (0-12).
 * @param weight The weight to set (0.0-1.0).
 * @throws IllegalArgumentException if `rank1` or `rank2` is not less than 13
 * or `rank1` is equal to `rank2` or `weight` is not in the range `[0.0, 1.0]`.
 */
internal fun Range.setWeightSuited(rank1: Int, rank2: Int, weight: Float) {
    require(rank1 in 0..12 && rank2 in 0..12) {
        "rank1 and rank2 must be between 0 and 12"
    }
    require(rank1 != rank2) { "rank1 and rank2 must be different" }
    require(weight in 0.0..1.0) { "weight must be between 0.0 and 1.0" }
    setWeight(suitedIndices(rank1, rank2), weight)
}

/**
 * Sets the weights of specified offsuit hands.
 *
 * @param rank1 The first rank (0-12).
 * @param rank2 The second rank (0-12).
 * @param weight The weight to set (0.0-1.0).
 * @throws IllegalArgumentException if `rank1` or `rank2` is not less than 13
 * or `rank1` is equal to `rank2` or `weight` is not in the range `[0.0, 1.0]`.
 */
internal fun Range.setWeightOffsuit(rank1: Int, rank2: Int, weight: Float) {
    require(rank1 in 0..12 && rank2 in 0..12) {
        "rank1 and rank2 must be between 0 and 12"
    }
    require(rank1 != rank2) { "rank1 and rank2 must be different" }
    require(weight in 0.0..1.0) { "weight must be between 0.0 and 1.0" }
    setWeight(offsuitIndices(rank1, rank2), weight)
}



/**
 * Checks if all the indices in the list have the same weight.
 *
 * @param indices The indices to check.
 * @return True if all indices have the same weight, false otherwise.
 */
private fun Range.isSameWeight(indices: List<Int>): Boolean {
    if (indices.isEmpty()) return true
    val firstWeight = data[indices[0]]
    return indices.all { data[it] == firstWeight }
}

/**
 * Generates strings for non-pair combinations and adds them to the result list.
 *
 * @param result The list to which non-pair strings will be added.
 */
private fun Range.nonPairsStrings(result: MutableList<String>) {
    for (rank1 in 12 downTo 1) {
        if (canUnsuit(rank1)) {
            highCardsStrings(result, rank1, Suitedness.All)
        } else {
            highCardsStrings(result, rank1, Suitedness.Suited)
            highCardsStrings(result, rank1, Suitedness.Offsuit)
        }
    }
}

/**
 * Checks if hands with different suitedness can be represented together.
 *
 * @param rank1 The first rank to check.
 * @return True if the combinations of different suitedness can
 * be represented together, false otherwise.
 */
private fun Range.canUnsuit(rank1: Int): Boolean {
    for (rank2 in 0 until rank1) {
        val sameSuited = isSameWeight(suitedIndices(rank1, rank2))
        val sameOffsuit = isSameWeight(offsuitIndices(rank1, rank2))
        val weightSuited = getWeightSuited(rank1, rank2)
        val weightOffsuit = getWeightOffsuit(rank1, rank2)
        if ((sameSuited && sameOffsuit && weightSuited != weightOffsuit)
            || (sameSuited != sameOffsuit && weightSuited > 0.0f && weightOffsuit > 0.0f)
        ) {
            return false
        }
    }
    return true
}

/**
 * Generates strings for pair combinations and adds them to the result list.
 *
 * @param result The list to which pair strings will be added.
 */
private fun Range.pairsStrings(result: MutableList<String>) {
    var start: Pair<Int, Float>? = null

    for (i in 12 downTo -1) {
        val rank = i
        val prevRank = i + 1
        val weightPair = if (i == -1) 0.0f else getWeightPair(rank)
        val isSameWeight = if (i == -1) false else isSameWeight(pairIndices(rank))

        if (start != null && (i == -1 || !isSameWeight || start.second != weightPair)) {
            val (startRank, weight) = start
            val s = rankToChar(startRank)
            val e = rankToChar(prevRank)
            val range = when (startRank) {
                prevRank -> "$s$s"
                12 -> "${e}${e}+"
                else -> "$s$s-${e}${e}"
            }
            if (weight != 1.0f) {
                result.add("$range:$weight")
            } else {
                result.add(range)
            }
            start = null
        }

        if (i >= 0 && isSameWeight && weightPair > 0.0f && start == null) {
            start = Pair(rank, weightPair)
        }
    }
}

/**
 * Generates strings for high card combinations and adds them to the result list.
 *
 * @param result The list to which high card strings will be added.
 * @param rank1 The high card rank.
 * @param suitedness The suitedness of the hands.
 */
private fun Range.highCardsStrings(
    result: MutableList<String>,
    rank1: Int,
    suitedness: Suitedness
) {
    val rank1Char = rankToChar(rank1)
    var start: Pair<Int, Float>? = null
    val (getter: (Int, Int) -> List<Int>, suitChar) = when (suitedness) {
        Suitedness.Suited -> Pair(::suitedIndices, "s")
        Suitedness.Offsuit -> Pair(::offsuitIndices, "o")
        Suitedness.All -> Pair(::nonPairIndices, "")
        else -> error("highCardsStrings: invalid suitedness")
    }
    for (i in (rank1 - 1) downTo -1) {
        val rank2 = i
        val prevRank2 = i + 1
        val averageWeight = if (i == -1) 0.0f else getAverageWeight(getter(rank1, rank2))
        val isSameWeight = if (i == -1) false else isSameWeight(getter(rank1, rank2))

        if (start != null && (i == -1 || !isSameWeight || start.second != averageWeight)) {
            val (startRank2, weight) = start
            val s = rankToChar(startRank2)
            val e = rankToChar(prevRank2)
            val range = when (startRank2) {
                prevRank2 -> "$rank1Char$s$suitChar"
                rank1 - 1 -> "$rank1Char$e$suitChar+"
                else -> "$rank1Char$s$suitChar-$rank1Char$e$suitChar"
            }
            if (weight != 1.0f) {
                result.add("$range:$weight")
            } else {
                result.add(range)
            }
            start = null
        }

        if (i >= 0 && isSameWeight && averageWeight > 0.0f && start == null) {
            start = Pair(rank2, averageWeight)
        }
    }
}

/**
 * Generates strings for suit-specified combinations and adds them to the result list.
 *
 * @param result The list to which suit-specified strings will be added.
 */
private fun Range.suitSpecifiedStrings(result: MutableList<String>) {
    // pairs
    for (rank in 12 downTo 0) {
        if (isSameWeight(pairIndices(rank))) continue
        for (suit1 in 3 downTo 0) {
            for (suit2 in (suit1 - 1) downTo 0) {
                val weight = getWeightByCards(4 * rank + suit1, 4 * rank + suit2)
                if (weight == 0.0f) continue
                val rankChar = rankToChar(rank)
                val suit1Char = suitToChar(suit1)
                val suit2Char = suitToChar(suit2)
                if (weight != 1.0f) {
                    result.add("$rankChar$suit1Char$rankChar$suit2Char:$weight")
                } else {
                    result.add("$rankChar$suit1Char$rankChar$suit2Char")
                }
            }
        }
    }
}
