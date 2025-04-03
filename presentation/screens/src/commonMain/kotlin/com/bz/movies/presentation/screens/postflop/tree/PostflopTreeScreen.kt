import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.bz.movies.presentation.components.PercentSuffix
import com.bz.movies.presentation.screens.postflop.tree.BetSize
import com.bz.movies.presentation.screens.postflop.tree.BetType
import com.bz.movies.presentation.screens.postflop.tree.PostflopTreViewModel
import com.bz.movies.presentation.screens.postflop.tree.StreetType
import com.bz.movies.presentation.screens.postflop.tree.TreeEditEvent
import com.bz.movies.presentation.screens.postflop.tree.TreeState
import com.bz.movies.presentation.utils.roundToDecimals
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_screen_bet
import movies_kmp.presentation.screens.generated.resources.postflop_screen_donk
import movies_kmp.presentation.screens.generated.resources.postflop_screen_flop
import movies_kmp.presentation.screens.generated.resources.postflop_screen_raise
import movies_kmp.presentation.screens.generated.resources.postflop_screen_river
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_IP_bet_sizes
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_OOP_bet_sizes
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_OOP_donk_size
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_add_all_in_threshold
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_effective_stack
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_force_all_in_threshold
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_merging_threshold
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_rake
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_rake_cap
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_starting_pot
import movies_kmp.presentation.screens.generated.resources.postflop_screen_turn
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
            modifier = Modifier.align(Alignment.Start).padding(vertical = 8.dp),
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
                onCheckedChange = { viewmodel.sendEvent(TreeEditEvent.DonksSwitched(it)) }
            )
        }

        BoardOOP(state = state, sendTreeEvent = viewmodel::sendEvent)

        Text(
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start).padding(vertical = 8.dp),
            text = stringResource(Res.string.postflop_screen_tree_IP_bet_sizes),
            textAlign = TextAlign.Center,
        )

        BoardIP(state = state, sendTreeEvent = viewmodel::sendEvent)

        OutlinedTextField(
            value = state.allInThreshold.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_add_all_in_threshold))
            },
            onValueChange = { viewmodel.sendEvent(TreeEditEvent.AllInThresholdUpdated(it)) },
            modifier = Modifier.padding(2.dp).wrapContentHeight(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        OutlinedTextField(
            value = state.forceAllInThreshold.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_force_all_in_threshold))
            },
            onValueChange = { viewmodel.sendEvent(TreeEditEvent.ForceAllInThresholdUpdated(it)) },
            modifier = Modifier.padding(2.dp).wrapContentHeight(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        OutlinedTextField(
            value = state.mergingThreshold.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_merging_threshold))
            },
            onValueChange = { viewmodel.sendEvent(TreeEditEvent.MergingThresholdUpdated(it)) },
            modifier = Modifier.padding(2.dp).wrapContentHeight(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

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
            onValueChange = { sendTreeEvent(TreeEditEvent.StartingPotUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        OutlinedTextField(
            value = state.effectiveStack.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_effective_stack))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.EffectiveStackUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )

        OutlinedTextField(
            value = state.rake.roundToDecimals(2).toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_rake))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.RakeUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { PercentSuffix() }
        )

        OutlinedTextField(
            value = state.rakeCap.toString(),
            label = {
                Text(text = stringResource(Res.string.postflop_screen_tree_rake_cap))
            },
            onValueChange = { sendTreeEvent(TreeEditEvent.RakeCapUpdated(it)) },
            modifier = Modifier.padding(2.dp).weight(1.0f),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        )
    }
}

@Composable
private fun BoardOOP(
    state: TreeState,
    sendTreeEvent: (TreeEditEvent) -> Unit,
) {
    Board(
        sendTreeEvent = sendTreeEvent,
        betSize = state.oopBetSize,
        isDonksEnabled = state.isDonksEnabled,
        betType = BetType.OOP,
    )
}

@Composable
private fun BoardIP(
    state: TreeState,
    sendTreeEvent: (TreeEditEvent) -> Unit,
) {
    Board(
        sendTreeEvent = sendTreeEvent,
        betSize = state.ipBetSize,
        isDonksEnabled = false,
        betType = BetType.IP,
    )
}

@Composable
private fun Board(
    sendTreeEvent: (TreeEditEvent) -> Unit,
    betType: BetType,
    betSize: BetSize,
    isDonksEnabled: Boolean,
) {
    Row(
        modifier = Modifier.padding(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Street(
            onBetUpdated = {
                sendTreeEvent(TreeEditEvent.BetUpdated(betType, StreetType.FLOP, it))
            },
            onRaiseUpdated = {
                sendTreeEvent(TreeEditEvent.RaiseUpdated(betType, StreetType.FLOP, it))
            },
            onDonkUpdated = {},
            bet = betSize.betFlop.joinToString(),
            raise = betSize.raiseFlop.joinToString(),
            streetName = stringResource(Res.string.postflop_screen_flop),
            modifier = Modifier.weight(1.0f).fillMaxHeight(),
            donk = null
        )
        Street(
            onBetUpdated = {
                sendTreeEvent(TreeEditEvent.BetUpdated(betType, StreetType.TURN, it))
            },
            onRaiseUpdated = {
                sendTreeEvent(TreeEditEvent.RaiseUpdated(betType, StreetType.TURN, it))
            },
            onDonkUpdated = {
                sendTreeEvent(TreeEditEvent.DonkUpdated(betType, StreetType.TURN, it))
            },
            bet = betSize.betTurn.joinToString(),
            raise = betSize.raiseTurn.joinToString(),
            streetName = stringResource(Res.string.postflop_screen_turn),
            modifier = Modifier.weight(1.0f),
            donk = if (isDonksEnabled) betSize.donkTurn.joinToString() else null
        )
        Street(
            onBetUpdated = {
                sendTreeEvent(TreeEditEvent.BetUpdated(betType, StreetType.RIVER, it))
            },
            onRaiseUpdated = {
                sendTreeEvent(TreeEditEvent.RaiseUpdated(betType, StreetType.RIVER, it))
            },
            onDonkUpdated = {
                sendTreeEvent(TreeEditEvent.DonkUpdated(betType, StreetType.RIVER, it))
            },
            bet = betSize.betRiver.joinToString(),
            raise = betSize.raiseRiver.joinToString(),
            streetName = stringResource(Res.string.postflop_screen_river),
            modifier = Modifier.weight(1.0f),
            donk = if (isDonksEnabled) betSize.donkRiver.joinToString() else null
        )
    }
}

@Composable
private fun Street(
    onBetUpdated: (String) -> Unit,
    onRaiseUpdated: (String) -> Unit,
    onDonkUpdated: (String) -> Unit,
    bet: String,
    raise: String,
    donk: String?,
    streetName: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .wrapContentHeight()
            .padding(4.dp)
    ) {
        Text(
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.align(Alignment.Start).padding(2.dp),
            text = streetName,
            textAlign = TextAlign.Center,
        )

        OutlinedTextField(
            value = bet,
            label = { Text(text = stringResource(Res.string.postflop_screen_bet)) },
            onValueChange = { onBetUpdated(it) },
            modifier = Modifier.padding(2.dp).wrapContentHeight(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { PercentSuffix() }
        )

        OutlinedTextField(
            value = raise,
            label = { Text(text = stringResource(Res.string.postflop_screen_raise)) },
            onValueChange = { onRaiseUpdated(it) },
            modifier = Modifier.padding(2.dp).wrapContentHeight(),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { PercentSuffix() }
        )

        if (donk != null) {
            OutlinedTextField(
                value = donk,
                label = { Text(text = stringResource(Res.string.postflop_screen_donk)) },
                onValueChange = { onDonkUpdated(it) },
                modifier = Modifier.padding(2.dp).wrapContentHeight(),
                maxLines = 1,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                suffix = { PercentSuffix() }
            )
        }

    }
}