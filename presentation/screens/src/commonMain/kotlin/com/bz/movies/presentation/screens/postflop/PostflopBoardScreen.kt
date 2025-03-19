import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.postflop.PostflopRangeViewModel
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.cards
import movies_kmp.presentation.screens.generated.resources.postflop_board_title
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun PostflopBoardScreen(
    viewmodel: PostflopRangeViewModel = koinViewModel(),
    modifier: Modifier = Modifier,
) {

    val state by viewmodel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(Res.string.postflop_board_title),
            modifier = modifier.padding(8.dp),
        )

        CardGrid() {}
    }
}

@Composable
internal fun CardGrid(
    modifier: Modifier = Modifier,
    onSelectedChanged: (Int) -> Unit
) {
    val cards = stringArrayResource(Res.array.cards)
    LazyHorizontalGrid(
        rows = GridCells.Fixed(4),
        modifier = modifier
            .aspectRatio(1.0f)
            .padding(12.dp)
            .border(width = 1.dp, color = Color.Black)
    ) {
        items(52) { handId ->

            val cellColor = when (handId % 4) {
                0 -> Color.Black
                1 -> Color.Red
                2 -> Color.Blue
                else -> Color.Green
            }

            Text(
                fontSize = 64.sp,
                color = cellColor,
                modifier = modifier
                    .wrapContentSize()
                    .padding(2.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(15))
                    .clickable { onSelectedChanged(handId) },
                text = cards[handId],
                textAlign = TextAlign.Center,
            )
        }
    }
}

