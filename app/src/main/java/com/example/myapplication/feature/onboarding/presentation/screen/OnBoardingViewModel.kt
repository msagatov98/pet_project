package com.example.myapplication.feature.onboarding.presentation.screen

import androidx.lifecycle.ViewModel
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.controller.DefaultScreenController
import com.example.myapplication.core.android.ui.presentation.controller.ScreenController
import com.example.myapplication.core.android.ui.presentation.controller.setEffectSafely
import com.example.myapplication.feature.onboarding.presentation.navigation.OnBoardingNavigator

class OnBoardingViewModel(
    private val uiRepository: UiRepository,
    private val onBoardingNavigator: OnBoardingNavigator,
) : ViewModel(), ScreenController<Action, UiState, Effect> by DefaultScreenController(UiState()) {

    override fun action(action: Action) {
        when (action) {
            Action.SkipOnBoarding -> {
                uiRepository.onBoardingSkipped()
                onBoardingNavigator.navigateToHome()
            }
        }
    }
}