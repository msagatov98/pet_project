package com.example.myapplication.feature.pokemon.presentation.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myapplication.core.android.ui.presentation.controller.DefaultScreenController
import com.example.myapplication.core.android.ui.presentation.controller.ScreenController
import com.example.myapplication.feature.pokemon.data.repository.PokemonRepository
import com.example.myapplication.feature.pokemon.presentation.model.Pokemon
import kotlinx.coroutines.flow.map

class PokemonListViewModel(
    repository: PokemonRepository,
) : ViewModel(),
    ScreenController<Any, PokemonListState, Any> by DefaultScreenController(PokemonListState()) {

    init {
        val paging = repository.getPokemonPagingSource()
            .map {
                it.map { Pokemon(id = it.id, name = it.name, url = it.imageUrl) }
            }
            .cachedIn(viewModelScope)
        setState(uiState.value.copy(pokemonPagingSource = paging))
    }
}