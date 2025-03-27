import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.postflop.tree.PostflopTreViewModel
import com.bz.movies.presentation.screens.postflop.tree.TreeEditEvent
import com.bz.movies.presentation.screens.postflop.tree.TreeState
import com.bz.movies.presentation.utils.roundToDecimals
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_OOP_bet_sizes
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_OOP_donk_size
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_effective_stack
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_rake
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_rake_cap
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_starting_pot
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun PostflopTreeConfigurationScreen(
    modifier: Modifier = Modifier,
    viewmodel: PostflopTreViewModel = koinViewModel(),
) {

    val state by viewmodel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {

        StackAndRakeConfig(state = state, sendTreeEvent = viewmodel::sendEvent)

        Text(
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start).padding(vertical = 16.dp),
            text = stringResource(Res.string.postflop_screen_tree_OOP_bet_sizes),
            textAlign = TextAlign.Center,
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                style = MaterialTheme.typography.bodyMedium,
                text = stringResource(Res.string.postflop_screen_tree_OOP_donk_size),
            )
            Checkbox(
                checked = state.isDonksEnabled,
                onCheckedChange = { viewmodel.sendEvent(TreeEditEvent.OnDonksEnabled(it)) }
            )
        }

        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            OutlinedTextField(
                value = state.rake.roundToDecimals(2).toString(),
                label = {
                    Text(text = stringResource(Res.string.postflop_screen_tree_rake))
                },
                onValueChange = { viewmodel.sendEvent(TreeEditEvent.OnRangeUpdated(it)) },
                modifier = Modifier.padding(2.dp).width(96.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                suffix = {
                    Text(
                        text = "%",
                        modifier = Modifier.wrapContentWidth(Alignment.End)
                    )
                }
            )

            OutlinedTextField(
                value = state.rakeCap.toString(),
                label = {
                    Text(text = stringResource(Res.string.postflop_screen_tree_rake_cap))
                },
                onValueChange = { viewmodel.sendEvent(TreeEditEvent.OnRangeUpdated(it)) },
                modifier = Modifier.padding(2.dp).width(96.dp),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            )
        }

    }
}

@Composable
private fun StackAndRakeConfig(
    state: TreeState,
    sendTreeEvent: (TreeEditEvent) -> Unit,
) {
    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        OutlinedTextField(
            value = state.startingPot.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_starting_pot))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.OnRangeUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        OutlinedTextField(
            value = state.effectiveStack.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_effective_stack))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.OnRangeUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        OutlinedTextField(
            value = state.rake.roundToDecimals(2).toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_rake))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.OnRangeUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = {
                Text(
                    text = "%",
                    modifier = Modifier.wrapContentWidth(Alignment.End)
                )
            }
        )

        OutlinedTextField(
            value = state.rakeCap.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_rake_cap))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.OnRangeUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}

