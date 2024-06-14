package com.example.myapplication.feature.onboarding

import androidx.navigation.NavController
import com.example.myapplication.common.ui.presentation.screen.Screen

class OnBoardingNavigator(
    private val navController: NavController,
) {

    fun navigateToHome() {
        navController.navigate(Screen.Pager) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }
}