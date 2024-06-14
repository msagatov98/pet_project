package com.example.myapplication.feature.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.myapplication.feature.home.data.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    repository: HomeRepository,
) : ViewModel() {

    val homeState = MutableStateFlow(HomeState())

    init {
        val paging = repository.getPokemonPagingSource().cachedIn(viewModelScope)
        homeState.update { it.copy(pokemonPagingSource = paging) }
    }
}