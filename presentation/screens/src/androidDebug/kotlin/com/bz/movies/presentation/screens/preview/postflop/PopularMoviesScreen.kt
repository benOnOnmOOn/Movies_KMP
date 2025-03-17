package com.bz.movies.presentation.screens.preview.postflop

import PostflopMainScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.popular.PopularMoviesScreen
import com.bz.movies.presentation.theme.MoviesTheme

@Preview(showBackground = true)
@Composable
private fun PostflopMainScreenPreview() {
    MoviesTheme {
        PostflopMainScreen()
    }
}
