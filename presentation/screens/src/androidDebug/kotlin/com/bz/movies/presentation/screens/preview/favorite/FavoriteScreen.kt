
package com.bz.movies.presentation.screens.preview.favorite

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.common.MoviesState
import com.bz.movies.presentation.screens.favorite.FavoriteScreen
import com.bz.movies.presentation.theme.MoviesTheme

@Preview(showBackground = true)
@Composable
private fun PlayingNowScreenPreview() {
    MoviesTheme {
        FavoriteScreen(state = MoviesState(), sendEvent = {})
    }
}
