package com.example.myapplication.core.android.ui.presentation.screen

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object OnBoarding : Screen

    @Serializable
    data object Home : Screen

    @Serializable
    data class PokemonDetail(
        val imageUrl: String,
        val name: String,
    ) : Screen

    @Serializable
    data object BS : Screen
}
