package com.bz.movies.presentation.screens.popular

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesContentWithPullToRefresh
import com.bz.movies.presentation.screens.common.MoviesState
import com.bz.movies.presentation.theme.MoviesTheme
import com.bz.presentation.screens.R

@Composable
fun PopularMoviesScreen(
    playingNowViewModel: PopularMoviesViewModel = hiltViewModel()
) {
    val playingNow by playingNowViewModel.state.collectAsState()
    PopularMoviesScreen(playingNow, playingNowViewModel::sendEvent)
}

@Composable
private fun PopularMoviesScreen(
    state: MoviesState = MoviesState(),
    sendEvent: (MovieEvent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.popular_now_screen_title))

        MoviesContentWithPullToRefresh(
            playingNowState = state,
            refresh = { sendEvent(MovieEvent.Refresh) },
            onMovieClicked = { sendEvent(MovieEvent.OnMovieClicked(it)) }
        )

    }

}

@Preview(showBackground = true)
@Composable
private fun PopularMoviesScreen() {
    MoviesTheme {
        PopularMoviesScreen()
    }
}
