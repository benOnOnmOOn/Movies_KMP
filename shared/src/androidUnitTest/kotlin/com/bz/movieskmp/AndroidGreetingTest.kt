package com.bz.movieskmp

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class AndroidGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Android"), "Check Android is mentioned")
    }
}
