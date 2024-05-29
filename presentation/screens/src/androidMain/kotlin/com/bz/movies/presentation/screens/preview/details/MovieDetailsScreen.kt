package com.bz.movies.presentation.screens.preview.details

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.common.MovieDetailState
import com.bz.movies.presentation.screens.details.MovieDetailsScreen
import com.bz.movies.presentation.theme.MoviesTheme

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun MovieDetailsScreenPreview() {
    MoviesTheme {
        MovieDetailsScreen(state = MovieDetailState())
    }
}
