package com.bz.movies.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

internal enum class RootRoute(val route: String, val isNavigationBarVisible : Boolean= true) {
    PlayingNow("tab_playing_now"),
    Popular("tab_popular"),
    Favorite("tab_favorite"),
//    More("tab_more"),
    Details("details/{id}"),
    Postflop("postflop",isNavigationBarVisible = false),
    PostflopRange("postflop/range",isNavigationBarVisible = false),
    PostflopIPRange("postflop/ip_range",isNavigationBarVisible = false),
    PostflopOOPRange("postflop/oop_range",isNavigationBarVisible = false),
    PostflopBoard("postflop/board",isNavigationBarVisible = false),
    PostflopTreeConfig("postflop/tree_configuartion",isNavigationBarVisible = false),
    PostflopRun("postflop/run",isNavigationBarVisible = false),
    PostflopResult("postflop/result",isNavigationBarVisible = false),
}

internal fun NavController.navigateToRootRoute(rootRoute: RootRoute) {
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
internal fun NavController.currentRootRouteAsState(): State<RootRoute> {
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
