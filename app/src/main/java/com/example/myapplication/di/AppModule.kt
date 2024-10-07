package com.example.myapplication.di

import com.example.myapplication.feature.imagepicker.di.imagePickerModule
import com.example.myapplication.feature.onboarding.di.onBoardingModule
import com.example.myapplication.feature.pokemon.di.pokemonModule
import com.example.myapplication.feature.settings.di.settingsModule

val appModule = listOf(
    presentationModule,
    networkModule,
    onBoardingModule,
    settingsModule,
    imagePickerModule,
    pokemonModule,
)
