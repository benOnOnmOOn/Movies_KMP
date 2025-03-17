package com.bz.movies.presentation.navigation

import PostflopMainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.bz.movies.presentation.screens.details.MovieDetailsScreen
import com.bz.movies.presentation.screens.empty.Greeting
import com.bz.movies.presentation.screens.favorite.FavoriteScreen
import com.bz.movies.presentation.screens.playingNow.PlayingNowScreen
import com.bz.movies.presentation.screens.popular.PopularMoviesScreen

@Composable
fun MoviesNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    MoviesNavHostInternal(navController, modifier)
}

@Composable
private fun MoviesNavHostInternal(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = RootRoute.PlayingNow.route,
    ) {
        composable(RootRoute.PlayingNow.route) {
            PlayingNowScreen()
        }
        composable(RootRoute.Popular.route) {
            PopularMoviesScreen(goToDetails = { navController.navigate("details/${it}") })
        }
        composable(RootRoute.Favorite.route) {
            FavoriteScreen()
        }
        composable(RootRoute.More.route) {
            PostflopMainScreen()
        }
        composable(
            route = RootRoute.Details.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
        ) { backStackEntry ->
            MovieDetailsScreen(backStackEntry.arguments?.getInt("id"))
        }
    }
}
