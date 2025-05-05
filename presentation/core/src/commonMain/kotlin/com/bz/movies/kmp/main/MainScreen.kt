package com.bz.movies.kmp.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.bz.movies.presentation.screens.main.MainMoviesScreen
import org.koin.compose.KoinContext

@Composable
internal fun MainScreen() {
    KoinContext {
        val navController = rememberNavController()

        MainMoviesScreen(navController = navController)
    }
}
