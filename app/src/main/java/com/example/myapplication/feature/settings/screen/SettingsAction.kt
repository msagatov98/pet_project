package com.example.myapplication.feature.settings.screen

import com.example.myapplication.core.android.ui.presentation.theme.AppTheme
import com.example.myapplication.core.android.ui.presentation.theme.ColorScheme

sealed interface SettingsAction {
    data class OnAppThemeSelected(val theme: AppTheme) : SettingsAction
    data class OnColorSchemeSelected(val scheme: ColorScheme) : SettingsAction
    data class OnLanguageSelected(val language: String) : SettingsAction
}