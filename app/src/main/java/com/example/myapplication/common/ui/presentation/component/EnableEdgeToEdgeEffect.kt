package com.example.myapplication.common.ui.presentation.component

import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.common.ui.presentation.AppState
import com.example.myapplication.common.ui.presentation.theme.AppTheme

@Composable
fun EnableEdgeToEdgeEffect(state: AppState) {
    val isDarkMode = isSystemInDarkTheme()
    val activity = LocalContext.current as ComponentActivity
    val color = MaterialTheme.colorScheme.background
    DisposableEffect(isDarkMode, state.colorScheme, state.appTheme) {

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

        activity.enableEdgeToEdge(
            statusBarStyle = systemBarStyle,
            navigationBarStyle = systemBarStyle,
        )
        onDispose { }
    }
}