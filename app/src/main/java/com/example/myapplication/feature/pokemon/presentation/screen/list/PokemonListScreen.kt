package com.example.myapplication.feature.pokemon.presentation.screen.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.myapplication.R
import com.example.myapplication.core.ext.errorMessage
import com.example.myapplication.core.ui.component.AppThemePreview
import com.example.myapplication.core.ui.component.ScreenPreview
import com.example.myapplication.core.ui.component.Shimmer
import com.example.myapplication.core.ui.component.pagingLoadStateItem
import com.example.myapplication.feature.pokemon.presentation.model.Pokemon
import com.example.myapplication.feature.pokemon.presentation.navigation.PokemonNavigator
import org.koin.androidx.compose.koinViewModel

@Composable
fun PokemonListScreen(navigator: PokemonNavigator) {
    val viewModel = koinViewModel<PokemonListViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    PokemonListScreen(
        state = state,
        onClick = { navigator.navigateToDetail(it) },
    )
}

@Composable
private fun PokemonListScreen(
    state: PokemonListState,
    onClick: (Pokemon) -> Unit,
) {
    val pagingData = state.pokemonPagingSource.collectAsLazyPagingItems()
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 24.dp),
    ) {
        item(
            span = { GridItemSpan(2) }
        ) {
            Text(
                text = stringResource(R.string.pokemon_title),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            )
        }

        when (val res = pagingData.loadState.refresh) {
            is LoadState.Error -> item(
                span = { GridItemSpan(2) },
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = "Error: ${res.error.errorMessage}",
                        textAlign = TextAlign.Center,
                    )
                }
            }

            LoadState.Loading -> items(20) {
                Shimmer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    cornerRadius = 12.dp,
                )
            }

            is LoadState.NotLoading -> {
                items(
                    count = pagingData.itemCount,
                ) {
                    val data = pagingData[it]
                    val onClickLambda = remember<(Pokemon) -> Unit> {
                        {
                            onClick(it)
                        }
                    }
                    if (data != null) {
                        PokemonCard(
                            pokemon = { data },
                            onClick = { onClickLambda(data) }
                        )
                    } else {
                        Shimmer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(120.dp),
                            cornerRadius = 12.dp,
                        )
                    }
                }
            }
        }

        pagingLoadStateItem(
            loadState = pagingData.loadState.append,
            loading = {
                Shimmer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    cornerRadius = 12.dp,
                )
            }
        )
    }
}

@ScreenPreview
@Composable
private fun PokemonListScreenPreview() {
    AppThemePreview {
        PokemonListScreen(
            state = PokemonListState(),
            onClick = { },
        )
    }
}
