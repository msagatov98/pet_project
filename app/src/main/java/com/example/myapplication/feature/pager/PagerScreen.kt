package com.example.myapplication.feature.pager

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.common.ui.presentation.screen.Screen
import com.example.myapplication.feature.home.presentation.HomeScreen
import com.example.myapplication.feature.profile.ProfileScreen
import com.example.myapplication.feature.search.SearchScreen

object PagerScreen {
    const val PAGER_SCREEN_COUNT = 3
    const val HOME_SCREEN_INDEX = 0
    const val SEARCH_SCREEN_INDEX = 1
    const val PROFILE_SCREEN_INDEX = 2
}

fun NavGraphBuilder.pagerScreen(
    pagerState: PagerState,
    navController: NavController,
) {
    composable<Screen.Pager> {
        PagerScreen(pagerState, navController)
    }
}

@Composable
fun PagerScreen(
    pagerState: PagerState,
    navController: NavController,
) {
    HorizontalPager(
        state = pagerState,
        userScrollEnabled = false,
        modifier = Modifier.fillMaxSize(),
    ) { screenIndex ->
        when (screenIndex) {
            PagerScreen.HOME_SCREEN_INDEX -> HomeScreen(navController = navController)
            PagerScreen.SEARCH_SCREEN_INDEX -> SearchScreen()
            PagerScreen.PROFILE_SCREEN_INDEX -> ProfileScreen()

            else -> Unit
        }
    }
}