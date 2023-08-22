@file:JvmName("FavoriteScreenViewModelKt")

package com.bz.movies.presentation.screens.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesContent
import com.bz.movies.presentation.screens.common.MoviesState
import com.bz.movies.presentation.theme.MoviesTheme
import com.bz.presentation.screens.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun FavoriteScreen(
    favoriteScreenViewModel: FavoriteScreenViewModel = koinViewModel()
) {
    val playingNow by favoriteScreenViewModel.state.collectAsState()
    FavoriteScreen(playingNow, favoriteScreenViewModel::sendEvent)
}

@Composable
private fun FavoriteScreen(
    state: MoviesState = MoviesState(),
    sendEvent: (MovieEvent) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.your_favorite_movies))

        MoviesContent(playingNowState = state) {
            sendEvent(MovieEvent.OnMovieClicked(it))
        }
    }

}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun PlayingNowScreenPreview() {
    MoviesTheme {
        FavoriteScreen()
    }
}
