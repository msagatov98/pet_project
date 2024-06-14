package com.example.myapplication.common.ui.theme

import androidx.compose.runtime.Immutable
import com.example.myapplication.feature.profile.AppTheme
import com.example.myapplication.feature.profile.ColorScheme

@Immutable
data class AppState(
    val appTheme: AppTheme = AppTheme.System,
    val colorScheme: ColorScheme = ColorScheme.Default,
)
