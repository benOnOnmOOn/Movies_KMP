import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bz.movies.presentation.navigation.RootRoute
import com.bz.movies.presentation.screens.postflop.PostflopMainViewModel
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_screen_board
import movies_kmp.presentation.screens.generated.resources.postflop_screen_ip_range
import movies_kmp.presentation.screens.generated.resources.postflop_screen_oop_range
import movies_kmp.presentation.screens.generated.resources.postflop_screen_range_editor
import movies_kmp.presentation.screens.generated.resources.postflop_screen_show_result
import movies_kmp.presentation.screens.generated.resources.postflop_screen_title
import movies_kmp.presentation.screens.generated.resources.postflop_screen_tree_config
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun PostflopMainScreen(
    postflopMainViewModel: PostflopMainViewModel = koinViewModel(),
    goToScreenEditor: (route: RootRoute) -> Unit = {},
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(Res.string.postflop_screen_title),
            modifier = modifier.padding(8.dp),
        )
        Button(
            modifier = modifier.padding(8.dp),
            onClick = { goToScreenEditor(RootRoute.PostflopRange) }) {
            Text(text = stringResource(Res.string.postflop_screen_range_editor))
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = { goToScreenEditor(RootRoute.PostflopIPRange) }) {
            Text(text = stringResource(Res.string.postflop_screen_ip_range))
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = { goToScreenEditor(RootRoute.PostflopOOPRange) }) {
            Text(text = stringResource(Res.string.postflop_screen_oop_range))
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = { goToScreenEditor(RootRoute.PostflopTreeConfig) }) {
            Text(text = stringResource(Res.string.postflop_screen_tree_config))
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = { goToScreenEditor(RootRoute.PostflopBoard) }) {
            Text(text = stringResource(Res.string.postflop_screen_board))
        }
        Button(
            modifier = modifier.padding(8.dp),
            onClick = { goToScreenEditor(RootRoute.PostflopResult) }) {
            Text(text = stringResource(Res.string.postflop_screen_show_result))
        }
    }
}

