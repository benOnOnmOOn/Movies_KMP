package com.bz.movies.presentation.screens.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.confirm_error_button
import movies_kmp.presentation.screens.generated.resources.dismiss_error_button_label
import movies_kmp.presentation.screens.generated.resources.generic_error_label
import movies_kmp.presentation.screens.generated.resources.generic_error_tittle
import movies_kmp.presentation.screens.generated.resources.ic_error
import movies_kmp.presentation.screens.generated.resources.no_internet_error_label
import movies_kmp.presentation.screens.generated.resources.no_internet_error_title
import movies_kmp.presentation.screens.generated.resources.try_again_network_error_button_label
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ErrorDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(Res.drawable.ic_error),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        title = {
            Text(text = stringResource(Res.string.generic_error_tittle))
        },
        text = {
            Text(text = stringResource(Res.string.generic_error_label))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
            ) {
                Text(stringResource(Res.string.confirm_error_button))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(stringResource(Res.string.dismiss_error_button_label))
            }
        },
    )
}

@Composable
internal fun NoInternetDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit = {},
    onConfirmation: () -> Unit = {},
) {
    AlertDialog(
        icon = {
            Icon(
                painter = painterResource(Res.drawable.ic_error),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        title = {
            Text(text = stringResource(Res.string.no_internet_error_title))
        },
        text = {
            Text(text = stringResource(Res.string.no_internet_error_label))
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                },
            ) {
                Text(text = stringResource(Res.string.try_again_network_error_button_label))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                },
            ) {
                Text(stringResource(Res.string.dismiss_error_button_label))
            }
        },
    )
}
