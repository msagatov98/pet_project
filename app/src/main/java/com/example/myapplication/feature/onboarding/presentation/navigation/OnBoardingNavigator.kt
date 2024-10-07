package com.example.myapplication.feature.onboarding.presentation.navigation

import androidx.navigation.NavController
import com.example.myapplication.core.ui.Screen

class OnBoardingNavigator(
    private val navController: NavController,
) {

    fun navigateToHome() {
        navController.navigate(Screen.Home) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}