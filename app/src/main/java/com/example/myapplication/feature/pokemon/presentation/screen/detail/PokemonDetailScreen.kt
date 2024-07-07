package com.example.myapplication.feature.pokemon.presentation.screen.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.core.android.ui.presentation.component.AppThemePreview
import com.example.myapplication.core.android.ui.presentation.component.ScreenPreview
import com.example.myapplication.core.android.ui.presentation.component.Shimmer
import com.example.myapplication.core.android.ui.presentation.screen.Screen
import com.example.myapplication.core.ext.errorMessage
import com.example.myapplication.core.resource.Resource
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.pokemonDetailScreen() {
    composable<Screen.PokemonDetail> {
        val pokemonDetail = remember { it.toRoute<Screen.PokemonDetail>() }
        val viewModel = koinViewModel<PokemonDetailViewModel> {
            parametersOf(pokemonDetail.name, pokemonDetail.imageUrl)
        }
        val uiState by viewModel.uiState.collectAsStateWithLifecycle()
        PokemonDetailScreen(uiState)
    }
}

@Composable
private fun PokemonDetailScreen(
    state: PokemonDetailState,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(state.imageUrl)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = state.name,
            modifier = Modifier
                .padding(8.dp)
        )

        when (val res = state.statsResources) {
            is Resource.Error -> {
                Text(
                    textAlign = TextAlign.Center,
                    text = res.exception.errorMessage,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }

            Resource.Loading -> {
                repeat(6) {
                    Shimmer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
            }

            is Resource.Success -> {
                res.data.forEach {
                    StateIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                        name = it.name,
                        value = it.value,
                    )
                }
            }
        }
    }
}

@ScreenPreview
@Composable
private fun PokemonDetailPreview() {
    AppThemePreview {
        PokemonDetailScreen(
            state = PokemonDetailState(name = "Pokemon Nane"),
        )
    }
}