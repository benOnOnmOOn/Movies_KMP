package com.bz.movies.presentation.screens.postflop.tree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bz.movies.presentation.screens.postflop.range.RangeEffect
import com.bz.movies.presentation.screens.postflop.utils.Range
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal class PostflopTreViewModel(
) : ViewModel() {
    private val _state = MutableStateFlow(TreeState())
    val state: StateFlow<TreeState> = _state.asStateFlow()

    private val _event: MutableSharedFlow<TreeEditEvent> = MutableSharedFlow()
    private val event: SharedFlow<TreeEditEvent> = _event.asSharedFlow()

    private val _effect: Channel<RangeEffect> = Channel()
    val effect = _effect.receiveAsFlow()

    /**
     * Combinations range for all possible card combination.
     * We are displaying only card singletons data,
     * and reuse all of this data when updating any weight in range
     */
    private var range: Range = Range()

    init {
        handleEvent()
    }

    fun sendEvent(event: TreeEditEvent) =
        viewModelScope.launch {
            _event.emit(event)
        }

    private fun handleEvent() =
        viewModelScope.launch {
            event.collect { handleEvent(it) }
        }

    private suspend fun handleEvent(event: TreeEditEvent) {

    }
}
