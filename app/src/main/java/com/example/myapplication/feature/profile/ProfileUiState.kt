package com.example.myapplication.feature.profile

import androidx.compose.runtime.Immutable

@Immutable
data class ProfileUiState(
    val appThemes: List<AppTheme> = listOf(AppTheme.Dark, AppTheme.Light, AppTheme.System),
    val colorSchemes: List<ColorScheme> = listOf(ColorScheme.Default, ColorScheme.Themed),
)

enum class AppTheme {
    Dark, Light, System
}

enum class ColorScheme {
    Default, Themed
}