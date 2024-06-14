package com.example.myapplication.common.ui.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.common.ui.presentation.AppState

enum class AppTheme {
    Dark, Light, System
}

enum class ColorScheme {
    Default, Themed
}

@Composable
fun AppTheme(
    state: AppState,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colorScheme = when (state.appTheme) {
        AppTheme.Dark -> darkScheme
        AppTheme.Light -> lightScheme
        AppTheme.System -> if (isSystemInDarkTheme()) darkScheme else lightScheme
    }

    MaterialTheme(
        content = content,
        colorScheme = when {
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
                val dynamicColorScheme = when (state.appTheme) {
                    AppTheme.Dark -> dynamicDarkColorScheme(context)
                    AppTheme.Light -> dynamicLightColorScheme(context)
                    AppTheme.System -> if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
                        context
                    )
                }

                when (state.colorScheme) {
                    ColorScheme.Default -> colorScheme
                    ColorScheme.Themed -> dynamicColorScheme
                }
            }

            else -> colorScheme
        },
    )
}