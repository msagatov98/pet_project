package com.example.myapplication.di

import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.myapplication.common.ui.data.UiRepository
import com.example.myapplication.common.ui.presentation.AppViewModel
import com.example.myapplication.feature.home.data.repository.HomeRepository
import com.example.myapplication.feature.home.data.repository.PokemonDatabase
import com.example.myapplication.feature.home.presentation.HomeViewModel
import com.example.myapplication.feature.onboarding.OnBoardingViewModel
import com.example.myapplication.feature.profile.ProfileViewModel
import com.example.myapplication.feature.second.SecondViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

private val presentationModule = module {

    single {
        androidContext().getSharedPreferences("sp", MODE_PRIVATE)
    }

    single {
        UiRepository(
            sharedPreferences = get()
        )
    }

    single {
        HomeRepository(
            pokemonDatabase = get(),
            httpClient = get(),
        )
    }

    viewModel {
        ProfileViewModel(
            uiRepository = get(),
        )
    }

    viewModel {
        HomeViewModel(
            repository = get(),
        )
    }

    viewModel { (name: String) ->
        SecondViewModel(
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
            uiRepository = get()
        )
    }
}

private val networkModule = module {

    single {
        HttpClient(OkHttp) {
            engine {
                addInterceptor(ChuckerInterceptor(get()))
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            defaultRequest {
                host = "pokeapi.co"
                url {
                    protocol = URLProtocol.HTTPS
                }
            }
        }
    }
}

private val dataBaseModule = module {

    single<PokemonDatabase> {
        Room
            .databaseBuilder(androidContext(), PokemonDatabase::class.java, "pokemon_database")
            .build()
    }
}

val appModule = presentationModule + networkModule + dataBaseModule
