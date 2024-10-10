package com.example.myapplication.core.ui.controller

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

@Immutable
interface ScreenController<Action : Any, UiState : Any, Effect : Any> {
    val uiState: StateFlow<UiState>
    val effect: Flow<Effect>
    fun action(action: Action)
    fun setState(uiState: UiState)
    suspend fun setEffect(effect: Effect)
}

fun <VM, Action : Any, UiState : Any, Effect : Any> VM.setEffectSafely(effect: Effect)
        where VM : ViewModel,
              VM : ScreenController<Action, UiState, Effect> {
    viewModelScope.launch {
        setEffect(effect)
    }
}

@OptIn(FlowPreview::class)
fun <T> Flow<T>.debounceEffect(): Flow<T> = debounce {
    if (it is DebounceEffect) it.debounce else 0
}

interface DebounceEffect {
    val debounce: Long get() = 300
}
