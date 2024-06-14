package com.example.myapplication.common.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.myapplication.feature.profile.ColorScheme
import com.example.myapplication.feature.profile.AppTheme as Theme

@Composable
fun AppTheme(
    state: AppState,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colorScheme = when (state.appTheme) {
        Theme.Dark -> darkScheme
        Theme.Light -> lightScheme
        Theme.System -> if (isSystemInDarkTheme()) darkScheme else lightScheme
    }

    MaterialTheme(
        content = content,
        colorScheme = when {
            (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
                val dynamicColorScheme = when (state.appTheme) {
                    Theme.Dark -> dynamicDarkColorScheme(context)
                    Theme.Light -> dynamicLightColorScheme(context)
                    Theme.System -> if (isSystemInDarkTheme()) dynamicDarkColorScheme(context) else dynamicLightColorScheme(
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