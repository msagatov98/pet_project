package com.example.myapplication.feature.profile

import androidx.lifecycle.ViewModel
import com.example.myapplication.common.ui.data.UiRepository
import com.example.myapplication.common.ui.presentation.controller.DefaultScreenController
import com.example.myapplication.common.ui.presentation.controller.ScreenController

class ProfileViewModel(
    private val uiRepository: UiRepository,
) : ViewModel(),
    ScreenController<ProfileAction, ProfileUiState, Nothing> by DefaultScreenController(
        ProfileUiState()
    ) {

    override fun action(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnLanguageSelected -> uiRepository.setLanguage(action.language)
            is ProfileAction.OnAppThemeSelected -> uiRepository.setAppTheme(action.theme.name)
            is ProfileAction.OnColorSchemeSelected -> uiRepository.setColorScheme(action.scheme.name)
        }
    }
}