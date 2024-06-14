package com.example.myapplication.feature.home.presentation

import androidx.navigation.NavController
import com.example.myapplication.common.ui.presentation.screen.Screen

class HomeNavigator(
    private val navController: NavController
) {

    fun navigateToSecond(data: String, name: String) {
        navController.navigate(Screen.Second(imageUrl = data, name = name))
    }
}