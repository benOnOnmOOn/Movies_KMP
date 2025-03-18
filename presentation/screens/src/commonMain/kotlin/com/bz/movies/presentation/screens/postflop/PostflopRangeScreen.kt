import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.postflop.PostflopRangeViewModel
import com.bz.movies.presentation.screens.postflop.RANKS
import com.bz.movies.presentation.screens.postflop.RangeEditEvent
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_range_clear
import movies_kmp.presentation.screens.generated.resources.postflop_range_combination
import movies_kmp.presentation.screens.generated.resources.postflop_range_screen_title
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt


@Composable
internal fun PostflopRangeScreen(
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
            text = stringResource(Res.string.postflop_range_screen_title),
            modifier = modifier.padding(8.dp),
        )

        LazyVerticalGrid(
            userScrollEnabled = false,
            columns = GridCells.Fixed(13),
            modifier = modifier
                .fillMaxSize()
                .aspectRatio(1.0f)
                .padding(12.dp)
                .border(width = 1.dp, color = Color.Black)
        ) {
            items(state.selectedHands.size) { handId ->
                val firstRank = 12 - handId % 13
                val secondRank = 12 - handId / 13
                val firstRankChar = RANKS[firstRank]
                val secondRankChar = RANKS[secondRank]
                val cardRank = if (firstRank == secondRank) {
                    "$firstRankChar$secondRankChar"
                } else if (firstRank > secondRank) {
                    "$firstRankChar${secondRankChar}o"
                } else {
                    "$secondRankChar${firstRankChar}s"
                }
                val unselectedColor = if (firstRank == secondRank) Color.LightGray else Color.Gray
                val cellColor = if (state.selectedHands[handId]) Color.Yellow else unselectedColor

                Text(
                    fontSize = 8.sp,
                    modifier = modifier
                        .border(width = 1.dp, color = Color.Black)
                        .background(color = cellColor)
                        .fillMaxSize()
                        .padding(2.dp)
                        .clickable { viewmodel.sendEvent(RangeEditEvent.OnCardClicked(handId)) },
                    text = cardRank,
                    textAlign = TextAlign.Center,
                )
            }
        }

        TextField(
            value = state.range,
            onValueChange = { viewmodel.sendEvent(RangeEditEvent.OnRangeUpdated(it)) },
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )
        Row {
            val combination = state.selectedHands.count { it }
            val percent: Float = combination * 100 / 169.0F
            val dec = percent.roundToDecimals(2)
            Text(
                text = stringResource(Res.string.postflop_range_combination, combination, dec),
                modifier = modifier.padding(8.dp).weight(1f),
            )

            Button(
                modifier = modifier.padding(8.dp),
                onClick = { viewmodel.sendEvent(RangeEditEvent.Clear) }) {
                Text(text = stringResource(Res.string.postflop_range_clear))
            }
        }
    }
}

fun Float.roundToDecimals(decimals: Int): Float {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    return (roundedValue / dotAt) + (roundedValue % dotAt).toFloat() / dotAt
}
