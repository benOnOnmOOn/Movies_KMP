package com.bz.movies.kmp.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.bz.movies.presentation.navigation.BottomNavigationBar
import com.bz.movies.presentation.navigation.MoviesNavHost
import com.bz.movies.presentation.navigation.currentRootRouteAsState
import com.bz.movies.presentation.navigation.navigateToRootRoute
import com.bz.movies.presentation.theme.MoviesTheme

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val currentRootRoute by navController.currentRootRouteAsState()

    MoviesTheme {
        Scaffold(
            bottomBar = {
                BottomNavigationBar(
                    currentRootRoute = currentRootRoute,
                    navigateToTopLevelDestination = {
                        navController.navigateToRootRoute(it.rootRoute)
                    },
                )
            },
        ) {
            Surface(
                modifier =
                    Modifier.fillMaxSize().padding(it),
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
