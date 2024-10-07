package com.example.myapplication.feature.onboarding.di

import com.example.myapplication.feature.onboarding.presentation.screen.OnBoardingViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val onBoardingModule = module {
    viewModelOf(::OnBoardingViewModel)
}