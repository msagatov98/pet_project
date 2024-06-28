package com.example.myapplication.feature.pokemon.presentation.screen.list

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.example.myapplication.feature.pokemon.data.model.Data
import com.example.myapplication.feature.pokemon.presentation.model.Pokemon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Immutable
data class PokemonListState(
    val pokemonPagingSource: Flow<PagingData<Pokemon>> = flow { PagingData.empty<Data.Result>() },
)
