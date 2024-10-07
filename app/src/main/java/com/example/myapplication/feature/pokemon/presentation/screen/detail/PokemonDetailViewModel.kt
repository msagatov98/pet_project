package com.example.myapplication.feature.pokemon.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.ui.DefaultScreenController
import com.example.myapplication.core.ui.ScreenController
import com.example.myapplication.feature.pokemon.data.repository.PokemonRepository
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val name: String,
    private val imageUrl: String,
    private val repository: PokemonRepository,
) : ViewModel(),
    ScreenController<Any, PokemonDetailState, Any> by DefaultScreenController(
        PokemonDetailState(
            name = name,
            imageUrl = imageUrl,
        )
    ) {

    init {
        viewModelScope.launch {
            val res = repository.getPokemon(name)
            setState(uiState.value.copy(statsResources = res))
        }
    }
}

