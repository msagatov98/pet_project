package com.example.myapplication.feature.settings

import androidx.compose.runtime.Immutable
import com.example.myapplication.core.android.ui.presentation.theme.AppTheme
import com.example.myapplication.core.android.ui.presentation.theme.ColorScheme

@Immutable
data class SettingsUiState(
    val appThemes: List<AppTheme> = listOf(AppTheme.Dark, AppTheme.Light, AppTheme.System),
    val colorSchemes: List<ColorScheme> = listOf(ColorScheme.Default, ColorScheme.Dynamic),
    val languages: List<String> = listOf("kk", "en", "ru"),
)
