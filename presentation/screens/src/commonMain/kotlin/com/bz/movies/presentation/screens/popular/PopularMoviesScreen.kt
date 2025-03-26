package com.bz.movies.presentation.screens.popular

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import co.touchlab.kermit.Logger
import com.bz.movies.presentation.screens.common.ErrorDialog
import com.bz.movies.presentation.screens.common.MovieEffect
import com.bz.movies.presentation.screens.common.MovieEvent
import com.bz.movies.presentation.screens.common.MoviesContentWithPullToRefresh
import com.bz.movies.presentation.screens.common.MoviesState
import com.bz.movies.presentation.screens.common.NoInternetDialog
import com.bz.movies.presentation.utils.collectInLaunchedEffectWithLifecycle
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun PopularMoviesScreen(
    playingNowViewModel: PopularMoviesViewModel = koinViewModel(),
    goToDetails : (Int) -> Unit
) {
    val noInternetDialog = remember { mutableStateOf(false) }
    val errorDialog = remember { mutableStateOf(false) }
    playingNowViewModel.effect.collectInLaunchedEffectWithLifecycle {
        when (it) {
            MovieEffect.NetworkConnectionError -> noInternetDialog.value = true
            MovieEffect.UnknownError -> errorDialog.value = true
        }
    }

    val state by playingNowViewModel.state.collectAsStateWithLifecycle()
    PopularMoviesScreen(
        state = state,
        showNoInternetDialog = noInternetDialog.value,
        showErrorDialog = errorDialog.value,
        onNetworkErrorDismiss = { noInternetDialog.value = false },
        onErrorDismiss = { errorDialog.value = false },
        sendEvent = {
            when (it) {
                is MovieEvent.OnMovieClicked -> goToDetails(it.movieItem.id)
                MovieEvent.Refresh -> playingNowViewModel.sendEvent(it)
            }
        },
    )
}

@Composable
internal fun PopularMoviesScreen(
    state: MoviesState = MoviesState(),
    sendEvent: (MovieEvent) -> Unit = {},
    onMovieClicked: (id: Int) -> Unit = {},
    showNoInternetDialog: Boolean = false,
    showErrorDialog: Boolean = false,
    onNetworkErrorDismiss: () -> Unit = {},
    onErrorDismiss: () -> Unit = {},
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MoviesContentWithPullToRefresh(
            playingNowState = state,
            refresh = { sendEvent(MovieEvent.Refresh) },
            onMovieClicked = {
                Logger.i("Item id : ${it.id}")
                sendEvent(MovieEvent.OnMovieClicked(it))
                onMovieClicked(it.id)
            },
            isRefreshing = state.isRefreshing,
        )

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
