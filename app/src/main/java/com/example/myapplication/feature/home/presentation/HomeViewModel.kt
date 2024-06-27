package com.example.myapplication.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myapplication.feature.home.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class HomeViewModel(
    repository: HomeRepository,
) : ViewModel() {

    val homeState = MutableStateFlow(HomeState())

    init {
        val paging = repository.getPokemonPagingSource()
            .map {
                it.map { Pokemon(id = it.id, name = it.name, url = it.imageUrl) }
            }
            .cachedIn(viewModelScope)
        homeState.update { it.copy(pokemonPagingSource = paging) }
    }
}