package com.example.myapplication.app.ui.presentation

import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.ext.empty
import com.example.myapplication.core.ui.screen.Screen
import com.example.myapplication.core.ui.component.BottomBar
import com.example.myapplication.core.ui.component.EnableEdgeToEdgeEffect
import com.example.myapplication.core.ui.theme.AppTheme
import com.example.myapplication.feature.home.HomeScreen
import com.example.myapplication.feature.home.homeScreen
import com.example.myapplication.feature.onboarding.presentation.navigation.OnBoardingNavigator
import com.example.myapplication.feature.onboarding.presentation.screen.onBoardingScreen
import com.example.myapplication.feature.pokemon.presentation.screen.detail.pokemonDetailScreen
import org.koin.androidx.compose.koinViewModel

fun AppCompatActivity.setAppContent() {
    setContent { Content() }
}

@Composable
private fun Content() {
    val viewModel: AppViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    AppTheme(state = state) {
        EnableEdgeToEdgeEffect(state)

        val pagerState = rememberPagerState { HomeScreen.HOME_SCREEN_COUNT }
        val navController = rememberNavController()
        val isBottomBarVisible = rememberIsBottomBarVisible(navController)

        LaunchedEffect(isBottomBarVisible) {
            viewModel.setBottomBarVisibility(isBottomBarVisible)
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
                label = String.empty,
                targetValue = if (isBottomBarVisible) {
                    it.calculateBottomPadding()
                } else {
                    it.calculateBottomPadding()
                }
            )
            NavHost(
                navController = navController,
                startDestination = state.startScreen,
                modifier = Modifier.padding(
                    bottom = bottomPadding,
                    top = it.calculateTopPadding(),
                )
            ) {
                onBoardingScreen(OnBoardingNavigator(navController))
                pokemonDetailScreen()
                homeScreen(navController = navController, pagerState = pagerState)
            }
        }
    }
}

@Composable
private fun rememberIsBottomBarVisible(navController: NavController): Boolean {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route.orEmpty()
    return remember(currentRoute) {
        currentRoute.contains(Screen.Home.javaClass.simpleName)
    }
}
