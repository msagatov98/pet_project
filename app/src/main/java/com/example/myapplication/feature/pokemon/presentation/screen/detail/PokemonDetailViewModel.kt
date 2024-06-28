package com.example.myapplication.feature.pokemon.presentation.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.feature.pokemon.data.repository.PokemonRepository
import com.example.myapplication.feature.pokemon.presentation.model.Stat
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PokemonDetailViewModel(
    private val name: String,
    private val repository: PokemonRepository,
) : ViewModel() {

    val list = MutableStateFlow<List<Stat>>(emptyList())

    init {
        viewModelScope.launch {
            when (val res = repository.getPokemon(name)) {
                is Resource.Error -> Unit
                Resource.Loading -> Unit
                is Resource.Success -> {
                    list.value = res.data
                }
            }
        }
    }
}

