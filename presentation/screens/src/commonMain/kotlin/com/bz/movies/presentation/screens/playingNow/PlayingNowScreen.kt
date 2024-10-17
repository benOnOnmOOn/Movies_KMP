package com.bz.movies.presentation.screens.playingNow

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesContentWithPullToRefresh
import com.bz.movies.presentation.screens.common.MoviesState
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.playing_now_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun PlayingNowScreen(playingNowViewModel: PlayingNowViewModel = koinViewModel()) {
    val playingNow by playingNowViewModel.state.collectAsState()
    PlayingNowScreen(playingNow, playingNowViewModel::sendEvent)
}

@Composable
private fun PlayingNowScreen(
    state: MoviesState = MoviesState(),
    sendEvent: (MovieEvent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(Res.string.playing_now_screen_title))

        MoviesContentWithPullToRefresh(
            playingNowState = state,
            refresh = { sendEvent(MovieEvent.Refresh) },
            onMovieClicked = { sendEvent(MovieEvent.OnMovieClicked(it)) },
            isRefreshing = state.isRefreshing,
        )
    }
}
