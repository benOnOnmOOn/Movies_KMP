package com.bz.movies.presentation.screens.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.common.ErrorDialog
import com.bz.movies.presentation.screens.common.MovieDetailState
import com.bz.movies.presentation.screens.common.MovieEffect
import com.bz.movies.presentation.screens.common.NoInternetDialog
import com.bz.movies.presentation.utils.collectInLaunchedEffectWithLifecycle
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.details_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Suppress("ComposeViewModelForwarding")
@Composable
internal fun MovieDetailsScreen(
    id: Int?,
    movieDetailsViewModel: MovieDetailsViewModel = koinViewModel(),
) {
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

    val noInternetDialog = remember { mutableStateOf(false) }
    val errorDialog = remember { mutableStateOf(false) }
    movieDetailsViewModel.effect.collectInLaunchedEffectWithLifecycle {
        when (it) {
            MovieEffect.NetworkConnectionError -> noInternetDialog.value = true
            MovieEffect.UnknownError -> errorDialog.value = true
        }
    }

    MovieDetailsScreen(
        state = state,
        showNoInternetDialog = noInternetDialog.value,
        showErrorDialog = errorDialog.value,
        onNetworkErrorDismiss = { noInternetDialog.value = false },
        onErrorDismiss = { errorDialog.value = false },
    )
}

@Composable
internal fun MovieDetailsScreen(
    state: MovieDetailState = MovieDetailState(),
    showNoInternetDialog: Boolean = false,
    showErrorDialog: Boolean = false,
    onNetworkErrorDismiss: () -> Unit = {},
    onErrorDismiss: () -> Unit = {},
) {
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

        if (showNoInternetDialog) {
            NoInternetDialog(
                onDismissRequest = { onNetworkErrorDismiss() },
                onConfirmation = { onNetworkErrorDismiss() },
            )
        }
        if (showErrorDialog) {
            ErrorDialog(
                onDismissRequest = { onErrorDismiss() },
                onConfirmation = { onErrorDismiss() },
            )
        }
    }
}
