package com.example.myapplication.core.android.ui.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.core.android.ui.presentation.component.BottomBar
import com.example.myapplication.core.android.ui.presentation.component.EnableEdgeToEdgeEffect
import com.example.myapplication.core.android.ui.presentation.screen.Screen
import com.example.myapplication.core.android.ui.presentation.theme.AppTheme
import com.example.myapplication.feature.onboarding.screen.onBoardingScreen
import com.example.myapplication.feature.home.HomeScreen
import com.example.myapplication.feature.home.homeScreen
import com.example.myapplication.feature.pokemon.presentation.screen.detail.pokemonDetailScreen
import org.koin.androidx.compose.koinViewModel

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
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
                    startDestination = state.startScreen,
                    modifier = Modifier.padding(
                        bottom = bottomPadding,
                        top = it.calculateTopPadding(),
                    )
                ) {
                    onBoardingScreen(navController)
                    pokemonDetailScreen()
                    homeScreen(navController = navController, pagerState = pagerState)
                }
            }
        }
    }
}
