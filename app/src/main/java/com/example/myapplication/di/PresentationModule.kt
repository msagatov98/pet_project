package com.example.myapplication.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.AppViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val presentationModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("sp", MODE_PRIVATE)
    }

    singleOf(::UiRepository)
    viewModelOf(::AppViewModel)
}

