package com.bz.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

enum class RootRoute(val route: String) {
    PlayingNow("tab_playing_now"),
    Popular("tab_popular"),
    Favorite("tab_favorite"),
    More("tab_more"),
    Details("details/{id}"),
}

fun NavController.navigateToRootRoute(rootRoute: RootRoute) {
    navigate(rootRoute.route) {
        popUpTo(graph.findStartDestination().route!!) {
            saveState = true
        }

        launchSingleTop = true
        restoreState = true
    }
}

@Stable
@Composable
fun NavController.currentRootRouteAsState(): State<RootRoute> {
    val selectedItem =
        remember { mutableStateOf(RootRoute.PlayingNow) }

    DisposableEffect(this) {
        val listener =
            NavController.OnDestinationChangedListener { _, destination, _ ->
                val item = RootRoute.entries.find { it.route == destination.route }
                selectedItem.value = item ?: RootRoute.PlayingNow
            }
        addOnDestinationChangedListener(listener)

        onDispose {
            removeOnDestinationChangedListener(listener)
        }
    }

    return selectedItem
}
