package com.bz.movies.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.common.MovieDetailState
import com.bz.movies.presentation.theme.MoviesTheme
import com.bz.presentation.screens.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    id: Int?,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel()
) {
    val playingNow by movieDetailsViewModel.state.collectAsState()
    if (id != null) {
        movieDetailsViewModel.fetchMovieDetails(id)
    }
    MovieDetailsScreen(playingNow)
}

@Composable
private fun MovieDetailsScreen(state: MovieDetailState = MovieDetailState()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.details_screen_title))

        state.movieDetails?.let {
            Text(text = it.title)
            Text(text = it.releaseDate)
            Text(text = it.language)
        }

    }

}

@Preview(showBackground = true)
@Composable
@Suppress("UnusedPrivateMember")
private fun MovieDetailsScreenPreview() {
    MoviesTheme {
        MovieDetailsScreen(state = MovieDetailState())
    }
}
