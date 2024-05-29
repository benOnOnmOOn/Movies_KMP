package com.bz.movies.presentation.navigation

import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.favorite
import movies_kmp.presentation.screens.generated.resources.ic_favorite
import movies_kmp.presentation.screens.generated.resources.ic_more
import movies_kmp.presentation.screens.generated.resources.ic_playing_now
import movies_kmp.presentation.screens.generated.resources.ic_popular
import movies_kmp.presentation.screens.generated.resources.more
import movies_kmp.presentation.screens.generated.resources.playing_now
import movies_kmp.presentation.screens.generated.resources.popular
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

data class TopLevelDestination(
    val rootRoute: RootRoute,
    val unselectedIcon: DrawableResource,
    val selectedIcon: DrawableResource,
    val iconTextId: StringResource,
)

val TOP_LEVEL_DESTINATIONS =
    listOf(
        TopLevelDestination(
            rootRoute = RootRoute.PlayingNow,
            unselectedIcon = Res.drawable.ic_playing_now,
            selectedIcon = Res.drawable.ic_playing_now,
            iconTextId = Res.string.playing_now,
        ),
        TopLevelDestination(
            rootRoute = RootRoute.Popular,
            unselectedIcon = Res.drawable.ic_popular,
            selectedIcon = Res.drawable.ic_popular,
            iconTextId = Res.string.popular,
        ),
        TopLevelDestination(
            rootRoute = RootRoute.Favorite,
            unselectedIcon = Res.drawable.ic_favorite,
            selectedIcon = Res.drawable.ic_favorite,
            iconTextId = Res.string.favorite,
        ),
        TopLevelDestination(
            rootRoute = RootRoute.More,
            unselectedIcon = Res.drawable.ic_more,
            selectedIcon = Res.drawable.ic_more,
            iconTextId = Res.string.more,
        ),
    )
