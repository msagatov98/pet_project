package com.example.myapplication.feature.pokemon.di

import androidx.room.Room
import com.example.myapplication.feature.pokemon.data.repository.PokemonDatabase
import com.example.myapplication.feature.pokemon.data.repository.PokemonRepository
import com.example.myapplication.feature.pokemon.data.source.PokemonDataSource
import com.example.myapplication.feature.pokemon.presentation.screen.detail.PokemonDetailViewModel
import com.example.myapplication.feature.pokemon.presentation.screen.list.PokemonListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val pokemonModule = module {
    singleOf(::PokemonRepository)
    singleOf(::PokemonDataSource)
    viewModelOf(::PokemonListViewModel)
    viewModelOf(::PokemonDetailViewModel)
    single<PokemonDatabase> {
        Room
            .databaseBuilder(androidContext(), PokemonDatabase::class.java, "pokemon_database")
            .build()
    }
}