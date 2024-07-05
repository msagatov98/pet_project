package com.example.myapplication.feature.onboarding.presentation.screen

import androidx.lifecycle.ViewModel
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.controller.DefaultScreenController
import com.example.myapplication.core.android.ui.presentation.controller.ScreenController
import com.example.myapplication.core.android.ui.presentation.controller.setEffectSafely

class OnBoardingViewModel(
    private val uiRepository: UiRepository,
) : ViewModel(), ScreenController<Action, UiState, Effect> by DefaultScreenController(UiState()) {

    override fun action(action: Action) {
        when (action) {
            Action.SkipOnBoarding -> {
                uiRepository.onBoardingSkipped()
                setEffectSafely(Effect.NavigateToHome)
            }
        }
    }
}