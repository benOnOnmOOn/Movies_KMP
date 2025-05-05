package com.bz.movies.presentation.components

import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_screen_percent
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun PercentSuffix(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(Res.string.postflop_screen_percent),
        modifier = Modifier.wrapContentWidth(Alignment.End)
    )
}