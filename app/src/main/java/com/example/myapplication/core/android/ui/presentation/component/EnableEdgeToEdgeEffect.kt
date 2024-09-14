package com.example.myapplication.core.android.ui.presentation.component

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.core.android.ui.presentation.AppState
import com.example.myapplication.core.android.ui.presentation.theme.AppTheme

@Composable
fun EnableEdgeToEdgeEffect(state: AppState) {
    val isDarkMode = isSystemInDarkTheme()
    val activity = LocalContext.current as ComponentActivity
    val color = MaterialTheme.colorScheme.background
    val bottomBarColor = MaterialTheme.colorScheme.surfaceContainer
    DisposableEffect(isDarkMode, state) {

        val systemBarStyle = SystemBarStyle.auto(
            lightScrim = color.toArgb(),
            darkScrim = color.toArgb(),
            detectDarkMode = {
                when (state.appTheme) {
                    AppTheme.Dark -> true
                    AppTheme.Light -> false
                    AppTheme.System -> isDarkMode
                }
            }
        )

        val bottomBarStyle = SystemBarStyle.auto(
            lightScrim = bottomBarColor.toArgb(),
            darkScrim = bottomBarColor.toArgb(),
            detectDarkMode = {
                when (state.appTheme) {
                    AppTheme.Dark -> true
                    AppTheme.Light -> false
                    AppTheme.System -> isDarkMode
                }
            }
        )

        val navigationBarState = if (state.isBottomBarVisible) bottomBarStyle else systemBarStyle

        activity.enableEdgeToEdge(
            statusBarStyle = systemBarStyle,
            navigationBarStyle = navigationBarState,
        )
        onDispose { }
    }
}