package com.example.myapplication.common.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.ui.theme.AppState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    uiRepository: UiRepository
) : ViewModel() {

    private val appTheme = uiRepository.appTheme
    private val colorScheme = uiRepository.colorScheme

    val state = combine(appTheme, colorScheme) { theme, scheme ->
        AppState(theme, scheme)
    }.stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = AppState())
}