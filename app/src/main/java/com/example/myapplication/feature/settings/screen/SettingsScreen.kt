package com.example.myapplication.feature.settings.screen

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.core.ui.component.AppThemePreview
import com.example.myapplication.core.ui.component.ScreenPreview
import com.example.myapplication.core.ui.component.Selector
import com.example.myapplication.core.ui.theme.AppTheme
import com.example.myapplication.core.ui.theme.ColorScheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun SettingsScreen() {
    val viewModel = koinViewModel<SettingsViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    SettingsScreen(
        state = state,
        onAction = viewModel::action
    )
}

@Composable
private fun SettingsScreen(
    state: SettingsUiState,
    onAction: (SettingsAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Selector(
            text = stringResource(R.string.settings_app_theme),
            options = state.appThemes.map { it.name },
            onSelect = {
                onAction(SettingsAction.OnAppThemeSelected(AppTheme.valueOf(it)))
            }
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Selector(
                text = stringResource(R.string.settings_color_scheme),
                options = state.colorSchemes.map { it.name },
                onSelect = {
                    onAction(SettingsAction.OnColorSchemeSelected(ColorScheme.valueOf(it)))
                }
            )
        }
        Selector(
            text = stringResource(R.string.settings_language),
            options = state.languages,
            onSelect = {
                onAction(SettingsAction.OnLanguageSelected(it))
            }
        )
    }
}

@ScreenPreview
@Composable
private fun SettingsScreenPreview() {
    AppThemePreview {
        SettingsScreen(
            state = SettingsUiState(),
            onAction = { },
        )
    }
}
