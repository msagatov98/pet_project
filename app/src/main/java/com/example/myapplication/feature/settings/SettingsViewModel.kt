package com.example.myapplication.feature.settings

import androidx.lifecycle.ViewModel
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.controller.DefaultScreenController
import com.example.myapplication.core.android.ui.presentation.controller.ScreenController

class SettingsViewModel(
    private val uiRepository: UiRepository,
) : ViewModel(),
    ScreenController<SettingsAction, SettingsUiState, Nothing> by DefaultScreenController(
        SettingsUiState()
    ) {

    override fun action(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnLanguageSelected -> uiRepository.setLanguage(action.language)
            is SettingsAction.OnAppThemeSelected -> uiRepository.setAppTheme(action.theme.name)
            is SettingsAction.OnColorSchemeSelected -> uiRepository.setColorScheme(action.scheme.name)
        }
    }
}