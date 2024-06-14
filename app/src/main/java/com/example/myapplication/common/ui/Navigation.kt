package com.example.myapplication.common.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.common.ui.component.BottomBar
import com.example.myapplication.feature.onboarding.onBoardingScreen
import com.example.myapplication.feature.pager.PagerScreen
import com.example.myapplication.feature.pager.pagerScreen
import com.example.myapplication.feature.second.secondScreen

@Composable
fun Navigation() {
    val pagerState = rememberPagerState { PagerScreen.PAGER_SCREEN_COUNT }
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isBottomBarVisible = remember(currentRoute) {
        currentRoute?.contains(Screen.Pager.javaClass.simpleName) == true
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (isBottomBarVisible) {
                BottomBar(pagerState = pagerState)
            }
        },
    ) {
        val bottomPadding by animateDpAsState(
            label = "",
            targetValue = if (isBottomBarVisible) {
                it.calculateBottomPadding()
            } else {
                it.calculateBottomPadding()
            }
        )
        NavHost(
            navController = navController,
            startDestination = Screen.OnBoarding,
            modifier = Modifier.padding(
                bottom = bottomPadding,
                top = it.calculateTopPadding(),
            )
        ) {
            onBoardingScreen(navController)
            secondScreen()
            pagerScreen(navController = navController, pagerState = pagerState)
        }
    }
}
