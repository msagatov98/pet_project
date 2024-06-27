package com.example.myapplication.feature.second

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.common.network.Resource
import com.example.myapplication.feature.home.data.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SecondViewModel(
    private val name: String,
    private val repository: HomeRepository,
) : ViewModel() {

    val list = MutableStateFlow<List<Stat>>(emptyList())

    init {
        viewModelScope.launch {
            val res = repository.getPokemon(name)

            when (res) {
                is Resource.Error -> Unit
                Resource.Loading -> Unit
                is Resource.Success -> {
                    list.value = res.data
                }
            }
        }
    }
}

data class Stat(
    val value: Int,
    val name: String
)
