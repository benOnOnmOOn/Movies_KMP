package com.bz.movies.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.details_screen_title
import movies_kmp.presentation.screens.generated.resources.playing_now_screen_title
import movies_kmp.presentation.screens.generated.resources.popular_now_screen_title
import movies_kmp.presentation.screens.generated.resources.postflop_board_title
import movies_kmp.presentation.screens.generated.resources.postflop_screen_ip_range
import movies_kmp.presentation.screens.generated.resources.postflop_screen_oop_range
import movies_kmp.presentation.screens.generated.resources.postflop_screen_run_solver
import movies_kmp.presentation.screens.generated.resources.postflop_screen_show_result
import movies_kmp.presentation.screens.generated.resources.postflop_screen_title
import movies_kmp.presentation.screens.generated.resources.postflop_tree_configuration_title
import movies_kmp.presentation.screens.generated.resources.your_favorite_movies
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val currentRootRoute by navController.currentRootRouteAsState()
    if (!currentRootRoute.isNavigationBarVisible) return
    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.surface,
    ) {
        TOP_LEVEL_DESTINATIONS.forEach { destination ->
            val isItemSelected = destination.rootRoute == currentRootRoute
            NavigationBarItem(
                selected = isItemSelected,
                onClick = { navController.navigateToRootRoute(destination.rootRoute) },
                icon = { NavIcon(destination, isItemSelected) },
                alwaysShowLabel = true,
                label = {
                    Text(
                        text = stringResource(destination.iconTextId),
                        color = getNavTextColor(isItemSelected),
                    )
                },
                colors =
                    NavigationBarItemDefaults.colors(
                        indicatorColor = MaterialTheme.colorScheme.surface,
                    ),
            )
        }
    }
}

@Composable
private fun getNavTextColor(isItemSelected: Boolean): Color =
    if (isItemSelected) {
        Color.Red
    } else {
        MaterialTheme.colorScheme.onBackground
    }

@Composable
private fun NavIcon(
    destination: TopLevelDestination,
    isItemSelected: Boolean,
) {
    return Icon(
        painter =
            painterResource(
                if (isItemSelected) {
                    destination.selectedIcon
                } else {
                    destination.unselectedIcon
                },
            ),
        contentDescription = stringResource(destination.iconTextId),
        tint =
            if (isItemSelected) {
                Color.Unspecified
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(navController: NavController, modifier: Modifier = Modifier) {
    val currentRootRoute by navController.currentRootRouteAsState()

    val title = when (currentRootRoute) {
        RootRoute.PlayingNow -> Res.string.playing_now_screen_title
        RootRoute.Popular -> Res.string.popular_now_screen_title
        RootRoute.Favorite -> Res.string.your_favorite_movies
        RootRoute.Details -> Res.string.details_screen_title
        RootRoute.Postflop -> Res.string.postflop_screen_title
        RootRoute.PostflopRange -> Res.string.postflop_screen_ip_range
        RootRoute.PostflopIPRange -> Res.string.postflop_screen_oop_range
        RootRoute.PostflopOOPRange -> Res.string.your_favorite_movies
        RootRoute.PostflopBoard -> Res.string.postflop_board_title
        RootRoute.PostflopTreeConfig -> Res.string.postflop_tree_configuration_title
        RootRoute.PostflopRun -> Res.string.postflop_screen_run_solver
        RootRoute.PostflopResult -> Res.string.postflop_screen_show_result
    }

    TopAppBar(title = { Text(text = stringResource(title)) })

}
