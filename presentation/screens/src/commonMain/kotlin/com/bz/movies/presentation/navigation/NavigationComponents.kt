package com.bz.movies.presentation.navigation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BottomNavigationBar(
    navController: NavController,
) {
    val currentRootRoute by navController.currentRootRouteAsState()

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
