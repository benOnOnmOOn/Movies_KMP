package com.bz.movies.presentation.screens.preview.playingNow

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.common.MoviesState
import com.bz.movies.presentation.screens.playingNow.PlayingNowScreen
import com.bz.movies.presentation.theme.MoviesTheme

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun PlayingNowScreenPreview() {
    MoviesTheme {
        PlayingNowScreen(state = MoviesState()) {}
    }
}
