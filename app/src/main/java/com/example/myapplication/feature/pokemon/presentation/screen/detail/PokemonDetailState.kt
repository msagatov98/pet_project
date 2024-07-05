package com.example.myapplication.feature.pokemon.presentation.screen.detail

import androidx.compose.runtime.Immutable
import com.example.myapplication.core.ext.empty
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.feature.pokemon.presentation.model.Stat

@Immutable
data class PokemonDetailState(
    val name: String = String.empty,
    val imageUrl: String = String.empty,
    val statsResources: Resource<List<Stat>> = Resource.Loading,
)
