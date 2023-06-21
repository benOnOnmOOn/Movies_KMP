package com.bz.movies.presentation.screens.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bz.movies.presentation.components.MovieContent

@Composable
fun MoviesContentWithPullToRefresh(
    playingNowState: MoviesState,
    refresh: () -> Unit = {},
    onMovieClicked: (MovieItem) -> Unit,
) {
    val pullRefreshState = rememberPullRefreshState(playingNowState.isRefreshing, refresh)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        MoviesContentLazyColumn(playingNowState, onMovieClicked)

        PullRefreshIndicator(
            playingNowState.isRefreshing,
            pullRefreshState,
            Modifier.align(Alignment.TopCenter)
        )

    }

}

@Composable
fun MoviesContent(
    playingNowState: MoviesState,
    onMovieClicked: (MovieItem) -> Unit,
) {
    Box(modifier = Modifier.fillMaxSize()) {
        MoviesContentLazyColumn(playingNowState, onMovieClicked)
    }
}

@Composable
private fun MoviesContentLazyColumn(
    playingNowState: MoviesState,
    onMovieClicked: (MovieItem) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        itemsIndexed(playingNowState.playingNowMovies) { index, movieItem ->
            MovieContent(
                movieItem = movieItem,
                isLast = index == playingNowState.playingNowMovies.size - 1,
                onMovieClicked = onMovieClicked
            )
        }
    }
}
