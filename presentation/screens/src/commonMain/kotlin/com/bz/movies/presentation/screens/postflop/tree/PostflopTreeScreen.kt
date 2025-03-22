import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.postflop.range.PostflopRangeViewModel
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_tree_configuration_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun PostflopTreeConfigurationScreen(
    modifier: Modifier = Modifier,
    viewmodel: PostflopRangeViewModel = koinViewModel(),
) {

    val state by viewmodel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(Res.string.postflop_tree_configuration_title),
            modifier = modifier.padding(8.dp),
        )

    }
}


