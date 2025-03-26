package com.bz.movies.kmp.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bz.movies.presentation.navigation.BottomNavigationBar
import com.bz.movies.presentation.navigation.MoviesNavHost
import com.bz.movies.presentation.navigation.TopNavigationBar
import com.bz.movies.presentation.theme.MoviesTheme
import org.koin.compose.KoinContext

@Composable
internal fun MainScreen() {
    KoinContext {
        val navController = rememberNavController()

        MoviesTheme {
            Scaffold(
                bottomBar = {
                    BottomNavigationBar(navController = navController)
                },
                topBar = {
                    TopNavigationBar(navController = navController)
                }
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize().padding(it),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    MoviesNavHost(
                        navController = navController,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }
}
