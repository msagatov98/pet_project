package com.example.myapplication.feature.onboarding.model

import androidx.annotation.DrawableRes

data class OnBoardingData(
    val title: String,
    val description: String,
    @DrawableRes
    val imageResId: Int,
)
