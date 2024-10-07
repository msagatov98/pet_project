package com.example.myapplication.app.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.app.ui.data.UiRepository
import com.example.myapplication.core.ui.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val uiRepository: UiRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(AppState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                uiRepository.appTheme,
                uiRepository.colorScheme,
                uiRepository.onBoardingSkipped
            ) { theme, scheme, skipped ->
                AppState(
                    appTheme = theme,
                    colorScheme = scheme,
                    startScreen = if (skipped) {
                        Screen.Home
                    } else {
                        Screen.OnBoarding
                    },
                )
            }.collect { newState ->
                _state.update {
                    it.copy(
                        appTheme = newState.appTheme,
                        colorScheme = newState.colorScheme,
                        startScreen = newState.startScreen,
                    )
                }
            }
        }
    }

    fun setBottomBarVisibility(isVisible: Boolean) {
        _state.update { it.copy(isBottomBarVisible = isVisible) }
    }
}