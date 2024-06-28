package com.example.myapplication.feature.onboarding.screen

import androidx.compose.runtime.Immutable
import com.example.myapplication.R
import com.example.myapplication.feature.onboarding.model.OnBoardingData

@Immutable
data class UiState(
    val data: List<OnBoardingData> = listOf(
        OnBoardingData(
            title = "Title on boarding 1",
            description = "Description on boarding 1",
            imageResId = R.drawable.image_on_boarding_1,
        ),
        OnBoardingData(
            title = "Title on boarding 2",
            description = "Description on boarding 2",
            imageResId = R.drawable.image_on_boarding_2
        ),
        OnBoardingData(
            title = "Title on boarding 3",
            description = "Description on boarding 3",
            imageResId = R.drawable.image_on_boarding_3
        ),
    ),
)

sealed interface Action {
    data object SkipOnBoarding : Action
}

sealed interface Effect {
    data object NavigateToHome : Effect
}