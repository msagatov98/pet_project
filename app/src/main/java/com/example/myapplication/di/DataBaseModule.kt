package com.example.myapplication.di

import androidx.room.Room
import com.example.myapplication.feature.pokemon.data.repository.PokemonDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataBaseModule = module {

    single<PokemonDatabase> {
        Room
            .databaseBuilder(androidContext(), PokemonDatabase::class.java, "pokemon_database")
            .build()
    }
}
