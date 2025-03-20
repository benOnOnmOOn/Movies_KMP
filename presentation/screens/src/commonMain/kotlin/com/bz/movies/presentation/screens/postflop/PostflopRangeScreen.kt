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
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.screens.common.ErrorDialog
import com.bz.movies.presentation.screens.postflop.PostflopRangeViewModel
import com.bz.movies.presentation.screens.postflop.RANKS
import com.bz.movies.presentation.screens.postflop.Range
import com.bz.movies.presentation.screens.postflop.RangeEditEvent
import com.bz.movies.presentation.screens.postflop.RangeEffect
import com.bz.movies.presentation.screens.postflop.getWeightOffsuit
import com.bz.movies.presentation.screens.postflop.getWeightPair
import com.bz.movies.presentation.screens.postflop.getWeightSuited
import com.bz.movies.presentation.utils.collectInLaunchedEffectWithLifecycle
import com.bz.movies.presentation.utils.roundToDecimals
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
    val errorDialog = remember { mutableStateOf(false) }
    viewmodel.effect.collectInLaunchedEffectWithLifecycle {
        when (it) {
            RangeEffect.RangeParsingError -> errorDialog.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(Res.string.postflop_range_screen_title),
            modifier = modifier.padding(8.dp),
        )

        HandGrid(
            range = state.range,
            onSelectedChanged = { first, second ->
                viewmodel.sendEvent(RangeEditEvent.OnCardClicked(first, second))
            }
        )

        TextField(
            value = state.range.toString(),
            onValueChange = { viewmodel.sendEvent(RangeEditEvent.OnRangeUpdated(it)) },
            modifier = modifier
                .padding(8.dp)
                .fillMaxWidth(),
        )

        CombinationCounter(
            range = state.range,
            onClear = { viewmodel.sendEvent(RangeEditEvent.Clear) }
        )

        HandSlider()
    }

    if (errorDialog.value) {
        ErrorDialog(
            onDismissRequest = { errorDialog.value = false },
            onConfirmation = { errorDialog.value = false },
        )
    }
}

@Composable
internal fun HandGrid(
    modifier: Modifier = Modifier,
    range: Range,
    onSelectedChanged: (Int, Int) -> Unit
) {
    LazyVerticalGrid(
        userScrollEnabled = false,
        columns = GridCells.Fixed(13),
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1.0f)
            .padding(12.dp)
            .border(width = 1.dp, color = Color.Black)
    ) {
        items(169) { handId ->
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
            val weight = if (firstRank == secondRank) {
                range.getWeightPair(firstRank)
            } else if (firstRank > secondRank) {
                range.getWeightOffsuit(firstRank, secondRank)
            } else {
                range.getWeightSuited(firstRank, secondRank)
            }
            val isSelected = weight > 0.0f
            val unselectedColor = if (firstRank == secondRank) Color.LightGray else Color.Gray
            val cellColor = if (isSelected) Color.Yellow else unselectedColor

            Text(
                fontSize = 8.sp,
                modifier = modifier
                    .border(width = 1.dp, color = Color.Black)
                    .background(color = cellColor)
                    .fillMaxSize()
                    .padding(2.dp)
                    .clickable { onSelectedChanged(firstRank, secondRank) },
                text = cardRank + "\n" + weight.roundToInt(),
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
internal fun CombinationCounter(
    modifier: Modifier = Modifier,
    range: Range,
    onClear: () -> Unit
) {
    Row {
        val combination = range.data.sum()
        val percent: Float = combination / range.data.size
        val dec = percent.roundToDecimals(2)
        Text(
            text = stringResource(Res.string.postflop_range_combination, combination, dec),
            modifier = modifier.padding(8.dp).weight(1f),
        )

        Button(
            modifier = modifier.padding(8.dp),
            onClick = { onClear() }) {
            Text(text = stringResource(Res.string.postflop_range_clear))
        }
    }
}


@Composable
internal fun HandSlider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(8.dp),
    ) {
        var sliderPosition by remember { mutableFloatStateOf(0f) }

        Slider(
            modifier = modifier.padding(8.dp).weight(1f),
            value = sliderPosition,
            onValueChange = { sliderPosition = it }
        )
        Text(
            modifier = modifier.fillMaxWidth(0.1f),
            text = sliderPosition.roundToDecimals(2).toString()
        )
    }
}


