package com.example.myapplication.feature.pokemon.presentation.model

import androidx.compose.runtime.Immutable

@Immutable
data class Stat(
    val value: Int,
    val name: String
)