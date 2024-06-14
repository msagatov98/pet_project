package com.example.myapplication.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.ui.UiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel(
    private val uiRepository: UiRepository,
) : ViewModel() {

    val state = MutableStateFlow(ProfileUiState())

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.OnAppThemeSelected -> uiRepository.setAppTheme(action.theme.name)
            is ProfileAction.OnColorSchemeSelected -> uiRepository.setColorScheme(action.scheme.name)
        }
    }
}