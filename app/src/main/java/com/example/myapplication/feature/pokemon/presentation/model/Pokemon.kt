package com.example.myapplication.feature.pokemon.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class Pokemon(
    val id: String,
    val name: String,
    val url: String,
)