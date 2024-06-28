package com.example.myapplication.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.myapplication.core.android.ui.presentation.screen.Screen
import com.example.myapplication.feature.pokemon.presentation.navigation.PokemonNavigator
import com.example.myapplication.feature.pokemon.presentation.screen.list.PokemonListScreen
import com.example.myapplication.feature.search.SearchScreen
import com.example.myapplication.feature.settings.SettingsScreen

object HomeScreen {
    const val HOME_SCREEN_COUNT = 3

    const val POKEMON_SCREEN_INDEX = 0
    const val SEARCH_SCREEN_INDEX = 1
    const val PROFILE_SCREEN_INDEX = 2
}

fun NavGraphBuilder.homeScreen(
    pagerState: PagerState,
    navController: NavController,
) {
    composable<Screen.Pager> {
        HorizontalPager(
            state = pagerState,
            userScrollEnabled = false,
            modifier = Modifier.fillMaxSize(),
        ) { screenIndex ->
            when (screenIndex) {
                HomeScreen.POKEMON_SCREEN_INDEX ->
                    PokemonListScreen(
                        navigator = remember { PokemonNavigator(navController) }
                    )

                HomeScreen.SEARCH_SCREEN_INDEX -> SearchScreen()
                HomeScreen.PROFILE_SCREEN_INDEX -> SettingsScreen()

                else -> Unit
            }
        }
    }
}
