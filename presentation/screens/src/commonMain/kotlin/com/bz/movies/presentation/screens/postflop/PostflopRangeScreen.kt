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
import com.bz.movies.presentation.screens.popular.PopularMoviesViewModel
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_screen_board
import movies_kmp.presentation.screens.generated.resources.postflop_screen_ip_range
import movies_kmp.presentation.screens.generated.resources.postflop_screen_oop_range
import movies_kmp.presentation.screens.generated.resources.postflop_screen_range_editor
import movies_kmp.presentation.screens.generated.resources.postflop_screen_show_result
import movies_kmp.presentation.screens.generated.resources.postflop_screen_title
import movies_kmp.presentation.screens.generated.resources.postflop_range_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun PostflopRangeScreen(
    playingNowViewModel: PopularMoviesViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(Res.string.postflop_range_screen_title),
            modifier = modifier.padding(8.dp),
        )

    }
}

