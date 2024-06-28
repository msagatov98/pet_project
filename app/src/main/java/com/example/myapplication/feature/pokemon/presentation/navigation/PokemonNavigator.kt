package com.example.myapplication.feature.pokemon.presentation.navigation

import androidx.navigation.NavController
import com.example.myapplication.core.android.ui.presentation.screen.Screen
import com.example.myapplication.feature.pokemon.presentation.model.Pokemon

class PokemonNavigator(
    private val navController: NavController
) {

    fun navigateToDetail(pokemon: Pokemon) {
        navController.navigate(Screen.PokemonDetail(imageUrl = pokemon.url, name = pokemon.name))
    }
}