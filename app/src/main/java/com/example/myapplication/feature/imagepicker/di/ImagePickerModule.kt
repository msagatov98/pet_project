package com.example.myapplication.feature.imagepicker.di

import com.example.myapplication.feature.imagepicker.screen.ImagePickerViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val imagePickerModule = module {
    viewModelOf(::ImagePickerViewModel)
}