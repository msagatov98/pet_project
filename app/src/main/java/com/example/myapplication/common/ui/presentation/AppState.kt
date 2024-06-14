package com.example.myapplication.common.ui.presentation

import androidx.compose.runtime.Immutable
import com.example.myapplication.common.ui.presentation.screen.Screen
import com.example.myapplication.common.ui.presentation.theme.AppTheme
import com.example.myapplication.common.ui.presentation.theme.ColorScheme

@Immutable
data class AppState(
    val appTheme: AppTheme = AppTheme.System,
    val colorScheme: ColorScheme = ColorScheme.Default,
    val startScreen: Screen = Screen.OnBoarding,
)
