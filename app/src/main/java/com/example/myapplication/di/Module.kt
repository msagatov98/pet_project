package com.example.myapplication.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.example.myapplication.common.ui.presentation.AppViewModel
import com.example.myapplication.common.ui.data.UiRepository
import com.example.myapplication.feature.home.data.HomeApi
import com.example.myapplication.feature.home.data.HomeRepository
import com.example.myapplication.feature.home.data.PokemonDao
import com.example.myapplication.feature.home.data.PokemonDatabase
import com.example.myapplication.feature.home.data.RemoteKeysDao
import com.example.myapplication.feature.home.presentation.HomeViewModel
import com.example.myapplication.feature.onboarding.OnBoardingViewModel
import com.example.myapplication.feature.profile.ProfileViewModel
import com.example.myapplication.feature.second.SecondViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

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
            homeApi = get(),
            pokemonDatabase = get()
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

    factory<HomeApi> {
        get<Retrofit>().create(HomeApi::class.java)
    }

    single {
        provideRetrofit(
            moshi = get(),
            okHttpClient = get(),
        )
    }

    single {
        provideMoshi()
    }

    single {
        provideOkHttp(
            context = androidContext(),
        )
    }
}

private val dataBaseModule = module {

    single<PokemonDatabase> {
        Room
            .databaseBuilder(androidContext(), PokemonDatabase::class.java, "pokemon_database")
            .build()
    }

    single<PokemonDao> {
        get<PokemonDatabase>().getPokemonDao()
    }

    single<RemoteKeysDao> {
        get<PokemonDatabase>().getRemoteKeysDao()
    }
}

val appModule = presentationModule + networkModule + dataBaseModule

private fun provideRetrofit(
    moshi: Moshi,
    okHttpClient: OkHttpClient,
): Retrofit {
    return Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl("https://pokeapi.co/api/v2/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
}

private fun provideMoshi(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}

private fun provideOkHttp(
    context: Context,
): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(ChuckerInterceptor(context))
        .build()
}