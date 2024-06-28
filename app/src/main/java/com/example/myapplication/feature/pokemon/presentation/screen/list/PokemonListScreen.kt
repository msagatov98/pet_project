package com.example.myapplication.feature.pokemon.presentation.screen.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.myapplication.R
import com.example.myapplication.core.android.ui.presentation.component.Shimmer
import com.example.myapplication.core.android.ui.presentation.component.rippleClickable
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
        modifier = Modifier.fillMaxSize(),
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
                        text = "Error: ${res.error.localizedMessage ?: res.error.message}",
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
                    if (data != null) {
                        PokemonCard(
                            pokemon = { data },
                            onClick = { onClick(data) }
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

fun LazyGridScope.pagingLoadStateItem(
    loadState: LoadState,
    keySuffix: String? = null,
    loading: (@Composable LazyGridItemScope.() -> Unit)? = null,
    error: (@Composable LazyGridItemScope.(LoadState.Error) -> Unit)? = null,
) {
    if (loading != null && loadState == LoadState.Loading) {
        items(
            count = 4,
            itemContent = { loading() },
        )
    }
    if (error != null && loadState is LoadState.Error) {
        item(
            content = { error(loadState) },
            key = keySuffix?.let { "errorItem_$it" },
            span = { GridItemSpan(2) },
        )
    }
}


@Composable
fun PokemonCard(
    pokemon: () -> Pokemon,
    onClick: () -> Unit,
) {
    val color = MaterialTheme.colorScheme.surfaceContainerHighest
    val poke = remember { pokemon() }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(16.dp))
            .drawBehind { drawRect(color) }
            .rippleClickable(onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = poke.url),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        Text(
            text = poke.name,
            modifier = Modifier.padding(8.dp),
        )
    }
}