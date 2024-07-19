package com.bz.movies.presentation.screens.preview.empty

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.empty.Greeting
import com.bz.movies.presentation.theme.MoviesTheme

@Preview(showBackground = true)
@Composable
@SuppressWarnings("UnusedPrivateMember")
private fun GreetingPreview() {
    MoviesTheme {
        Greeting("Android")
    }
}
