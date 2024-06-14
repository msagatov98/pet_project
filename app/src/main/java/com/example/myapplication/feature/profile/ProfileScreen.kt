package com.example.myapplication.feature.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.myapplication.R
import com.example.myapplication.common.ui.presentation.component.Selector
import com.example.myapplication.common.ui.presentation.theme.AppTheme
import com.example.myapplication.common.ui.presentation.theme.ColorScheme
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen() {
    val viewModel = koinViewModel<ProfileViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    ProfileScreen(
        state = state,
        onAction = viewModel::action
    )
}

@Composable
private fun ProfileScreen(
    state: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Selector(
            text = stringResource(R.string.profile_app_theme),
            options = state.appThemes.map { it.name },
            onSelect = {
                onAction(ProfileAction.OnAppThemeSelected(AppTheme.valueOf(it)))
            }
        )
        Selector(
            text = stringResource(R.string.profile_color_scheme),
            options = state.colorSchemes.map { it.name },
            onSelect = {
                onAction(ProfileAction.OnColorSchemeSelected(ColorScheme.valueOf(it)))
            }
        )
        Selector(
            text = stringResource(R.string.profile_language),
            options = state.languages,
            onSelect = {
                onAction(ProfileAction.OnLanguageSelected(it))
            }
        )
    }
}
