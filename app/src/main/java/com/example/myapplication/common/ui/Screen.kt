package com.example.myapplication.common.ui

import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {

    @Serializable
    data object OnBoarding : Screen

    @Serializable
    data object Pager : Screen

    @Serializable
    data class Second(
        val imageUrl: String,
        val name: String,
    ) : Screen
}
