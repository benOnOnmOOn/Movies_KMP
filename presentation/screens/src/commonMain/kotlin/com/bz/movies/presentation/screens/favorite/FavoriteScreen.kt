package com.bz.movies.presentation.screens.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.common.ErrorDialog
import com.bz.movies.presentation.screens.common.MovieEffect
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesContent
import com.bz.movies.presentation.screens.common.MoviesState
import com.bz.movies.presentation.screens.common.NoInternetDialog
import com.bz.movies.presentation.utils.collectInLaunchedEffectWithLifecycle
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.your_favorite_movies
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
internal fun FavoriteScreen(favoriteScreenViewModel: FavoriteScreenViewModel = koinViewModel()) {
    val state by favoriteScreenViewModel.state.collectAsStateWithLifecycle()

    val noInternetDialog = remember { mutableStateOf(false) }
    val errorDialog = remember { mutableStateOf(false) }
    favoriteScreenViewModel.effect.collectInLaunchedEffectWithLifecycle {
        when (it) {
            MovieEffect.NetworkConnectionError -> noInternetDialog.value = true
            MovieEffect.UnknownError -> errorDialog.value = true
        }
    }

    FavoriteScreen(
        state = state,
        showNoInternetDialog = noInternetDialog.value,
        showErrorDialog = errorDialog.value,
        onNetworkErrorDismiss = { noInternetDialog.value = false },
        onErrorDismiss = { errorDialog.value = false },
        sendEvent = favoriteScreenViewModel::sendEvent,
    )
}

@Composable
internal fun FavoriteScreen(
    sendEvent: (MovieEvent) -> Unit,
    state: MoviesState = MoviesState(),
    showNoInternetDialog: Boolean = false,
    showErrorDialog: Boolean = false,
    onNetworkErrorDismiss: () -> Unit = {},
    onErrorDismiss: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = stringResource(Res.string.your_favorite_movies))

        MoviesContent(playingNowState = state) {
            sendEvent(MovieEvent.OnMovieClicked(it))
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
