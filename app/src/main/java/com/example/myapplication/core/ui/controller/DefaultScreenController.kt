package com.example.myapplication.core.ui.controller

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

open class DefaultScreenController<Action : Any, UiState : Any, Effect : Any>(
    initialUiState: UiState,
) : ScreenController<Action, UiState, Effect> {

    override val uiState: StateFlow<UiState>
        get() = _uiState
    override val effect: Flow<Effect>
        get() = _effect.debounceEffect()
    private val _uiState = MutableStateFlow(initialUiState)
    private val _effect = MutableSharedFlow<Effect>()

    override fun action(action: Action) = Unit

    override fun setState(uiState: UiState) {
        _uiState.update { uiState }
    }

    override suspend fun setEffect(effect: Effect) {
        _effect.emit(effect)
    }
}