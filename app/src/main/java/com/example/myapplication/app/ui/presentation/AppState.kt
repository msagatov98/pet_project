package com.example.myapplication.app.ui.presentation

import androidx.compose.runtime.Immutable
import com.example.myapplication.core.ui.Screen
import com.example.myapplication.core.ui.theme.AppTheme
import com.example.myapplication.core.ui.theme.ColorScheme

@Immutable
data class AppState(
    val appTheme: AppTheme = AppTheme.System,
    val colorScheme: ColorScheme = ColorScheme.Default,
    val startScreen: Screen = Screen.OnBoarding,
    val isBottomBarVisible: Boolean = false,
)
