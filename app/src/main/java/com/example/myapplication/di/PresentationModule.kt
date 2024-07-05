package com.example.myapplication.di

import android.content.Context.MODE_PRIVATE
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.AppViewModel
import com.example.myapplication.feature.onboarding.presentation.screen.OnBoardingViewModel
import com.example.myapplication.feature.pokemon.data.repository.PokemonRepository
import com.example.myapplication.feature.pokemon.presentation.screen.detail.PokemonDetailViewModel
import com.example.myapplication.feature.pokemon.presentation.screen.list.PokemonListViewModel
import com.example.myapplication.feature.imagepicker.ImagePickerViewModel
import com.example.myapplication.feature.pokemon.data.mapper.DataToPokemonMapper
import com.example.myapplication.feature.pokemon.data.source.PokemonDataSource
import com.example.myapplication.feature.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    single {
        DataToPokemonMapper()
    }

    single {
        androidContext().getSharedPreferences("sp", MODE_PRIVATE)
    }

    single {
        UiRepository(
            sharedPreferences = get(),
        )
    }

    single {
        PokemonRepository(
            mapper = get(),
            pokemonDatabase = get(),
            pokemonDataSource = get(),
        )
    }

    single {
        PokemonDataSource(
            httpClient = get(),
        )
    }

    viewModel {
        SettingsViewModel(
            uiRepository = get(),
        )
    }

    viewModel {
        PokemonListViewModel(
            repository = get(),
        )
    }

    viewModel { (name: String, imageUrl: String) ->
        PokemonDetailViewModel(
            name = name,
            imageUrl = imageUrl,
            repository = get(),
        )
    }

    viewModel {
        AppViewModel(
            uiRepository = get(),
        )
    }

    viewModel {
        OnBoardingViewModel(
            uiRepository = get(),
        )
    }

    viewModel {
        ImagePickerViewModel()
    }
}

