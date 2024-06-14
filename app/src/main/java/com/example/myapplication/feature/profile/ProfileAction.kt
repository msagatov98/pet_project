package com.example.myapplication.feature.profile

import com.example.myapplication.common.ui.presentation.theme.AppTheme
import com.example.myapplication.common.ui.presentation.theme.ColorScheme

sealed interface ProfileAction {
    data class OnAppThemeSelected(val theme: AppTheme) : ProfileAction
    data class OnColorSchemeSelected(val scheme: ColorScheme) : ProfileAction
    data class OnLanguageSelected(val language: String) : ProfileAction
}