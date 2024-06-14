package com.example.myapplication.feature.profile

import androidx.compose.runtime.Immutable
import com.example.myapplication.common.ui.presentation.theme.AppTheme
import com.example.myapplication.common.ui.presentation.theme.ColorScheme

@Immutable
data class ProfileUiState(
    val appThemes: List<AppTheme> = listOf(AppTheme.Dark, AppTheme.Light, AppTheme.System),
    val colorSchemes: List<ColorScheme> = listOf(ColorScheme.Default, ColorScheme.Themed),
    val languages: List<String> = listOf("kk", "en", "ru"),
)
