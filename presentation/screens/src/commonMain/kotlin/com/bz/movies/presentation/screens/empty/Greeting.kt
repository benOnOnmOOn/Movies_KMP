package com.bz.movies.presentation.screens.empty

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
internal fun Greeting(
    name: String,
    modifier: Modifier = Modifier,
) {
    Column {
        Text(
            text = "Hello $name!",
            modifier = modifier,
        )
    }
}
