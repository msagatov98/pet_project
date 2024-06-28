package com.example.myapplication.feature.onboarding.navigation

import androidx.navigation.NavController
import com.example.myapplication.core.android.ui.presentation.screen.Screen

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