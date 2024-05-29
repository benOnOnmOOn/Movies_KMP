package com.bz.movies.presentation.screens.preview.empty

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.bz.movies.presentation.screens.empty.Greeting
import com.bz.movies.presentation.theme.MoviesTheme



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MoviesTheme {
        Greeting("Android")
    }
}
