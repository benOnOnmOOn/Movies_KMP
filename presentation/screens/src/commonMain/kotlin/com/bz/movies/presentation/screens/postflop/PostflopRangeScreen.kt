import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
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
import movies_kmp.presentation.screens.generated.resources.postflop_range_slider_percent
import movies_kmp.presentation.screens.generated.resources.postflop_range_slider_weight
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
internal fun PostflopRangeScreen(
    modifier: Modifier = Modifier,
    viewmodel: PostflopRangeViewModel = koinViewModel()
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

        CombinationCounter(
            range = state.range,
            onClear = { viewmodel.sendEvent(RangeEditEvent.Clear) },
            inputRange = state.inputRange,
            isInputError = state.inputError,
            onRangeUpdated = { viewmodel.sendEvent(RangeEditEvent.OnRangeUpdated(it)) },
        )

        HandSlider(
            sliderPosition = state.weight,
            onSliderPositionChanged = { viewmodel.sendEvent(RangeEditEvent.OnWeightUpdated(it)) },
        )
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
    range: Range,
    modifier: Modifier = Modifier,
    onSelectedChanged: (Int, Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(13),
        modifier = Modifier
            .fillMaxWidth()
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
            Box(
                modifier = Modifier
                    .padding(0.dp)
                    .border(width = 1.dp, color = Color.Black)
                    .background(color = cellColor)
                    .aspectRatio(1.0f)
                    .clickable { onSelectedChanged(firstRank, secondRank) },
            ) {
                Text(
                    fontSize = 8.sp,
                    modifier = modifier.align(Alignment.Center),
                    text = cardRank,
                    textAlign = TextAlign.Center,
                )
                if (weight > 0 && weight < 1) {
                    Text(
                        fontSize = 4.sp,
                        modifier = modifier.align(Alignment.BottomStart).wrapContentSize(),
                        text = weight.roundToDecimals(1).toString(),
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}

@Composable
internal fun CombinationCounter(
    range: Range,
    inputRange: String,
    onClear: () -> Unit,
    onRangeUpdated: (String) -> Unit,
    modifier: Modifier = Modifier,
    isInputError: Boolean = false
) {
    Row {
        val combination = range.data.sum()
        val percent: Float = combination / range.data.size * 100
        val dec = percent.roundToDecimals(2)

        OutlinedTextField(
            value = inputRange,
            onValueChange = { onRangeUpdated(it) },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            supportingText = {
                Text(
                    text = stringResource(Res.string.postflop_range_combination, combination, dec),
                    modifier = Modifier.fillMaxWidth(),
                )
            },
            maxLines = 2,
            isError = isInputError
        )

        Button(
            modifier = Modifier.padding(8.dp),
            onClick = { onClear() }) {
            Text(text = stringResource(Res.string.postflop_range_clear))
        }
    }
}


@Composable
internal fun HandSlider(
    sliderPosition: Float,
    modifier: Modifier = Modifier,
    onSliderPositionChanged: (Float) -> Unit
) {
    Row(
        modifier = Modifier.padding(8.dp).fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = stringResource(Res.string.postflop_range_slider_weight)
        )
        Slider(
            modifier = Modifier.padding(8.dp).weight(1.0f).wrapContentHeight(),
            value = sliderPosition,
            onValueChange = { onSliderPositionChanged(it) },
        )

        OutlinedTextField(
            value = stringResource(
                Res.string.postflop_range_slider_percent,
                sliderPosition * 100
            ),
            onValueChange = { onSliderPositionChanged((it.toFloatOrNull() ?: 0f) / 100) },
            modifier = Modifier.padding(2.dp).width(96.dp),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = {
                Text(
                    text = "%",
                    modifier = modifier.wrapContentWidth(Alignment.End)
                )
            }
        )
    }
}


