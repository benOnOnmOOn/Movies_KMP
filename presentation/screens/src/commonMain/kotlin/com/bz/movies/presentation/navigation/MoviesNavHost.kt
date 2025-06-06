package com.bz.movies.presentation.navigation

import PostflopBoardScreen
import PostflopMainScreen
import PostflopRangeScreen
import PostflopTreeConfigurationScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.savedstate.read
import com.bz.movies.presentation.screens.details.MovieDetailsScreen
import com.bz.movies.presentation.screens.empty.Greeting
import com.bz.movies.presentation.screens.favorite.FavoriteScreen
import com.bz.movies.presentation.screens.playingNow.PlayingNowScreen
import com.bz.movies.presentation.screens.popular.PopularMoviesScreen

@Composable
internal fun MoviesNavHost(
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
        composable(RootRoute.Postflop.route) {
            PostflopMainScreen(goToScreenEditor = {
                when (it) {
                    RootRoute.PostflopRange ,
                    RootRoute.PostflopIPRange ,
                    RootRoute.PostflopOOPRange ,
                    RootRoute.PostflopBoard ,
                    RootRoute.PostflopTreeConfig ,
                    RootRoute.PostflopRun ,
                    RootRoute.PostflopResult -> navController.navigate(it.route)
                    else -> error("Wrong navigation destination $it")
                }
            })
        }
        composable(RootRoute.PostflopRange.route) {
           PostflopRangeScreen()
        }
        composable(RootRoute.PostflopIPRange.route) {
            Greeting("Screen name = RootRoute.PostflopIPRange")
        }
        composable(RootRoute.PostflopOOPRange.route) {
            Greeting("Screen name = RootRoute.PostflopOOPRange")
        }
        composable(RootRoute.PostflopTreeConfig.route) {
            PostflopTreeConfigurationScreen()
        }
        composable(RootRoute.PostflopBoard.route) {
            PostflopBoardScreen()
        }
        composable(RootRoute.PostflopRun.route) {
            Greeting("Screen name = RootRoute.PostflopRun")
        }
        composable(RootRoute.PostflopResult.route) {
            Greeting("Screen name = RootRoute.PostflopResult")
        }
        composable(
            route = RootRoute.Details.route,
            arguments = listOf(navArgument("id") { type = NavType.IntType }),
        ) { backStackEntry ->
            MovieDetailsScreen(backStackEntry.arguments?.read { getInt("id")})
        }
    }
}
