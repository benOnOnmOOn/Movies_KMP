package com.bz.movies.presentation.screens.postflop.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RangeTest {

    @Test
    fun testCardToIndex() {
        var k = 0
        for (i in 0..51) {
            for (j in (i + 1)..51) {
                assertEquals(actual = cardPairToIndex(i, j), expected = k)
                assertEquals(actual = cardPairToIndex(j, i), expected = k)
                assertEquals(actual = indexToCardPair(k), expected = i to j)
                k += 1
            }
        }
    }

    @Test
    fun testRangeRegex() {
        val tests: List<Pair<String, Pair<String?, String?>>> = listOf(
            "AK" to ("AK" to null),
            "ak" to ("ak" to null),
            "K9s:.67" to ("K9s" to ".67"),
            "88+:1." to ("88+" to "1."),
            "98s-65s:0.25" to ("98s-65s" to "0.25"),
            "AcKh" to ("AcKh" to null),
            "8h8s+:.67" to ("8h8s+" to ".67"),
            "9d8d-6d5d:0.25" to ("9d8d-6d5d" to "0.25"),
            "AKQ" to (null to null),
            "AK+-AJ" to (null to null),
            "K9s.67" to (null to null),
            "88+:2.0" to (null to null),
            "98s-21s" to (null to null),
        )

        for ((input, expected) in tests) {
            val expectedRange = expected.first
            val expectedWeight = expected.second
            if (expectedRange != null) {
                val match = RANGE_REGEX.toRegex().find(input)
                assertTrue(match != null)
                val range = match.groups["range"]?.value
                assertEquals(expectedRange, range)
                val weight = match.groups["weight"]?.value
                assertEquals(expectedWeight, weight)
            } else {
                assertFalse(RANGE_REGEX.toRegex().matches(input))
            }
        }
    }


    @Test
    fun testRangeFromStr() {
        val pairPlus = "88+".toRange()
        val pairPlusEquiv = "AA,KK,QQ,JJ,TT,99,88".toRange()
        assertEquals(pairPlus, pairPlusEquiv)

        val pairPlusSuit = "8s8h+".toRange()
        val pairPlusSuitEquiv = "AhAs,KhKs,QhQs,JhJs,ThTs,9h9s,8h8s".toRange()
        assertEquals(pairPlusSuit, pairPlusSuitEquiv)

        val connectorPlus = "98s+".toRange()
        val connectorPlusEquiv = "AKs,KQs,QJs,JTs,T9s,98s".toRange()
        assertEquals(connectorPlus, connectorPlusEquiv)

        val otherPlus = "A8o+".toRange()
        val otherPlusEquiv = "AKo,AQo,AJo,ATo,A9o,A8o".toRange()
        assertEquals(otherPlus, otherPlusEquiv)

        val pairDash = "88-55".toRange()
        val pairDashEquiv = "88,77,66,55".toRange()
        assertEquals(pairDash, pairDashEquiv)

        val connectorDash = "98s-65s".toRange()
        val connectorDashEquiv = "98s,87s,76s,65s".toRange()
        assertEquals(connectorDash, connectorDashEquiv)

        val gapperDash = "AQo-86o".toRange()
        val gapperDashEquiv = "AQo,KJo,QTo,J9o,T8o,97o,86o".toRange()
        assertEquals(gapperDash, gapperDashEquiv)

        val otherDash = "K5-K2".toRange()
        val otherDashEquiv = "K5,K4,K3,K2".toRange()
        assertEquals(otherDash, otherDashEquiv)

        val suitCompound = "AhAs-QhQs,JJ".toRange()
        val suitCompoundEquiv = "JJ,AhAs,KhKs,QhQs".toRange()
        assertEquals(suitCompound, suitCompoundEquiv)

        val allowEmpty = "".toRange()
        assertEquals(allowEmpty, Range())

        val allowTrailingComma = "AK,".toRange()
        assertTrue(allowTrailingComma.data.isNotEmpty())

//        assertFailsWith<IllegalStateException> { "AK,,".toRange() }
        assertFailsWith<IllegalStateException> { "89".toRange() }
        assertFailsWith<IllegalStateException> { "AAo".toRange() }
        assertFailsWith<IllegalStateException> { "AQo:1.1".toRange() }
        assertFailsWith<IllegalStateException> { "AQo-AQo".toRange() }
        assertFailsWith<IllegalStateException> { "AQo-86s".toRange() }
        assertFailsWith<IllegalStateException> { "AQo-KQo".toRange() }
        assertFailsWith<IllegalStateException> { "K2-K5".toRange() }
        assertFailsWith<IllegalStateException> { "AhAs-QsQh".toRange() }

        val data = "85s:0.5".toRange()
        assertEquals(0.5f, data.getWeightSuited(3, 6))
        assertEquals(0.5f, data.getWeightSuited(6, 3))
        assertEquals(0.0f, data.getWeightOffsuit(3, 6))
        assertEquals(0.0f, data.getWeightOffsuit(6, 3))
    }

    @Test
    fun testRangeToString() {
        val tests = listOf(
            "AA,KK" to "KK+",
            "KK,QQ" to "KK-QQ",
            "66-22,TT+" to "TT+,66-22",
            "AA:0.5, KK:1.0, QQ:1.0, JJ:0.5" to "AA:0.5,KK-QQ,JJ:0.5",
            "AA,AK,AQ" to "AA,AQ+",
            "AK,AQ,AJs" to "AJs+,AQo+",
            "KQ,KT,K9,K8,K6,K5" to "KQ,KT-K8,K6-K5",
            "AhAs-QhQs,JJ" to "JJ,AsAh,KsKh,QsQh",
            "KJs+,KQo,KsJh" to "KJs+,KQo,KsJh",
            "KcQh,KJ" to "KJ,KcQh",
        )

        for ((input, expected) in tests) {
            val range = input.toRange()
            assertEquals(expected, range.toString())
        }
    }
}