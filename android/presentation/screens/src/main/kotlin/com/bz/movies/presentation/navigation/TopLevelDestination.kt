package com.bz.movies.presentation.navigation

import com.bz.presentation.screens.R

data class TopLevelDestination(
    val rootRoute: RootRoute,
    val unselectedIcon: Int,
    val selectedIcon: Int,
    val iconTextId: Int,
)

val TOP_LEVEL_DESTINATIONS =
    listOf(
        TopLevelDestination(
            rootRoute = RootRoute.PlayingNow,
            unselectedIcon = R.drawable.ic_playing_now,
            selectedIcon = R.drawable.ic_playing_now,
            iconTextId = R.string.playing_now,
        ),
        TopLevelDestination(
            rootRoute = RootRoute.Popular,
            unselectedIcon = R.drawable.ic_popular,
            selectedIcon = R.drawable.ic_popular,
            iconTextId = R.string.popular,
        ),
        TopLevelDestination(
            rootRoute = RootRoute.Favorite,
            unselectedIcon = R.drawable.ic_favorite,
            selectedIcon = R.drawable.ic_favorite,
            iconTextId = R.string.favorite,
        ),
        TopLevelDestination(
            rootRoute = RootRoute.More,
            unselectedIcon = R.drawable.ic_more,
            selectedIcon = R.drawable.ic_more,
            iconTextId = R.string.more,
        ),
    )
