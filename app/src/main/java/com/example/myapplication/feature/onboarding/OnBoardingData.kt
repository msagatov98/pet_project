package com.example.myapplication.feature.onboarding

import androidx.annotation.DrawableRes

data class OnBoardingData(
    val title: String,
    val description: String,
    @DrawableRes
    val imageResId: Int,
)
