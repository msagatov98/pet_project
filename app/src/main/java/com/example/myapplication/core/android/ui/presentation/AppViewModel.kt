package com.example.myapplication.core.android.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.screen.Screen
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class AppViewModel(
    uiRepository: UiRepository,
) : ViewModel() {

    val state = combine(
        uiRepository.appTheme,
        uiRepository.colorScheme,
        uiRepository.onBoardingSkipped
    ) { theme, scheme, skipped ->
        AppState(
            appTheme = theme,
            colorScheme = scheme,
            startScreen = if (skipped) {
                Screen.Pager
            } else {
                Screen.OnBoarding
            }
        )
    }.stateIn(viewModelScope, started = SharingStarted.Eagerly, initialValue = AppState())
}