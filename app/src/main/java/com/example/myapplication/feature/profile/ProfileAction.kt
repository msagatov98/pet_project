package com.example.myapplication.feature.profile

sealed interface ProfileAction {
    data class OnAppThemeSelected(val theme: AppTheme) : ProfileAction
    data class OnColorSchemeSelected(val scheme: ColorScheme) : ProfileAction
}