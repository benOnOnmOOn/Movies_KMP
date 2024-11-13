package com.bz.movies.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.common.MovieDetailState
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.details_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun MovieDetailsScreen(
    id: Int?,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel(),
) {
    @Suppress("DeprecatedCall")
    LaunchedEffect(id) {
        if (id != null) {
            movieDetailsViewModel.fetchMovieDetails(id)
        }
    }

    MovieDetailsScreen(movieDetailsViewModel)
}

@Composable
internal fun MovieDetailsScreen(movieDetailsViewModel: MovieDetailsViewModel) {
    val state by movieDetailsViewModel.state.collectAsStateWithLifecycle()
    MovieDetailsScreen(state)
}

@Composable
internal fun MovieDetailsScreen(state: MovieDetailState = MovieDetailState()) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize(),
    ) {
        Text(text = stringResource(Res.string.details_screen_title))

        state.movieDetails?.let {
            Text(text = it.title)
            Text(text = it.releaseDate)
            Text(text = it.language)
        }
    }
}
