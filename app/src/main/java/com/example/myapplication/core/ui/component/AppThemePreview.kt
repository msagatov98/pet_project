package com.example.myapplication.core.ui.component

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.example.myapplication.app.ui.presentation.AppState
import com.example.myapplication.core.ui.theme.AppTheme

@Composable
fun AppThemePreview(
    isDarkTheme: Boolean = false,
    content: @Composable () -> Unit,
) {
    AppTheme(
        state = AppState(
            appTheme = if (isDarkTheme) {
                AppTheme.Dark
            } else {
                AppTheme.Light
            }
        ),
    ) {
        Surface(content = content)
    }
}