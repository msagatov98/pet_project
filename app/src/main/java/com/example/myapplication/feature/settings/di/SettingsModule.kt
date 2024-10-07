package com.example.myapplication.feature.settings.di

import com.example.myapplication.feature.settings.screen.SettingsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val settingsModule = module {
    viewModelOf(::SettingsViewModel)
}