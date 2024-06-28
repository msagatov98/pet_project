package com.example.myapplication.di

import android.content.Context.MODE_PRIVATE
import com.example.myapplication.core.android.ui.data.UiRepository
import com.example.myapplication.core.android.ui.presentation.AppViewModel
import com.example.myapplication.feature.onboarding.screen.OnBoardingViewModel
import com.example.myapplication.feature.pokemon.data.repository.PokemonRepository
import com.example.myapplication.feature.pokemon.presentation.screen.detail.PokemonDetailViewModel
import com.example.myapplication.feature.pokemon.presentation.screen.list.PokemonListViewModel
import com.example.myapplication.feature.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

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
            pokemonDatabase = get(),
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

    viewModel { (name: String) ->
        PokemonDetailViewModel(
            name = name,
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
}

