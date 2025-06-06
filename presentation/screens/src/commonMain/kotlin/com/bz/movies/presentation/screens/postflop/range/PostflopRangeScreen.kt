import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bz.movies.presentation.components.PercentSuffix
import com.bz.movies.presentation.screens.common.ErrorDialog
import com.bz.movies.presentation.screens.postflop.range.PostflopRangeViewModel
import com.bz.movies.presentation.screens.postflop.range.RangeEditEvent
import com.bz.movies.presentation.screens.postflop.range.RangeEffect
import com.bz.movies.presentation.utils.collectInLaunchedEffectWithLifecycle
import com.bz.movies.presentation.utils.roundToDecimals
import movies_kmp.presentation.screens.generated.resources.Res
import movies_kmp.presentation.screens.generated.resources.postflop_range_clear
import movies_kmp.presentation.screens.generated.resources.postflop_range_combination
import movies_kmp.presentation.screens.generated.resources.postflop_range_slider_percent
import movies_kmp.presentation.screens.generated.resources.postflop_range_slider_weight
import movies_kmp.presentation.screens.generated.resources.postflop_screen_range
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
        HandGrid(
            weights = state.weights,
            cardsSingletons = state.cardsSingleton,
            onSelectedChanged = { handId ->
                viewmodel.sendEvent(RangeEditEvent.OnCardClicked(handId))
            }
        )

        CombinationCounter(
            combinations = state.combinations,
            combinationsPercent = state.compinationPercent,
            onClear = { viewmodel.sendEvent(RangeEditEvent.Clear) },
            inputRange = state.inputRange,
            isInputError = state.inputError,
            onRangeUpdated = { viewmodel.sendEvent(RangeEditEvent.OnRangeUpdated(range = it)) },
        )

        HandSlider(
            sliderPosition = state.inputWeight,
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
    weights: FloatArray,
    cardsSingletons: List<String>,
    modifier: Modifier = Modifier,
    onSelectedChanged: (Int) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(13),
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.0f)
            .padding(12.dp)
            .border(width = 1.dp, color = Color.Black)
    ) {
        items(weights.size) { handId ->
            val weight = weights[handId]
            val singleton = cardsSingletons[handId]
            val isSelected = weight > 0.0f
            val unselectedColor = if (singleton.length == 2) Color.LightGray else Color.Gray
            val cellColor = if (isSelected) Color.Yellow else unselectedColor

            val text = buildAnnotatedString {

                if (weight > 0 && weight < 1) {
                    val weight = stringResource(
                        Res.string.postflop_range_slider_percent,
                        (weight * 100).roundToDecimals(2)
                    )
                    withStyle(SpanStyle(fontSize = 4.sp)) {
                        appendLine()
                    }
                    append(singleton)
                    withStyle(SpanStyle(fontSize = 4.sp)) {
                        appendLine()
                        append(weight)
                    }
                } else {
                    append(singleton)
                }
            }

            Box(
                modifier = Modifier
                    .border(width = 1.dp, color = Color.Black)
                    .fillMaxSize()
                    .background(color = cellColor)
                    .aspectRatio(1.0f)
                    .clickable { onSelectedChanged(handId) },
            ) {
                Text(
                    fontSize = 8.sp,
                    modifier = Modifier.align(Alignment.Center),
                    text = text,
                    lineHeight = 1.em,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
internal fun CombinationCounter(
    inputRange: String,
    onClear: () -> Unit,
    combinations: Float,
    combinationsPercent: Float,
    onRangeUpdated: (String) -> Unit,
    modifier: Modifier = Modifier,
    isInputError: Boolean = false
) {
    Row {
        OutlinedTextField(
            value = inputRange,
            label = {
                Text(text = stringResource(Res.string.postflop_screen_range))
            },
            onValueChange = { onRangeUpdated(it) },
            modifier = Modifier
                .padding(8.dp)
                .weight(1f),
            supportingText = {
                Text(
                    text = stringResource(
                        Res.string.postflop_range_combination,
                        combinations,
                        combinationsPercent
                    ),
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
            suffix = { PercentSuffix() }
        )
    }
}


