package com.example.myapplication.feature.home.presentation

import androidx.compose.runtime.Immutable

@Immutable
data class Pokemon(
    val id: String,
    val name: String,
    val url: String,
)