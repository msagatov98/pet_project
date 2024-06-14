package com.example.myapplication.feature.home.presentation

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
import androidx.compose.material3.Card
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
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.common.ui.component.Shimmer
import com.example.myapplication.feature.home.data.Data
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(navController: NavController) {
    val viewModel = koinViewModel<HomeViewModel>()
    val navigator = remember { HomeNavigator(navController) }
    val state by viewModel.homeState.collectAsStateWithLifecycle()
    HomeScreen(
        state = state,
        onClick = { navigator.navigateToSecond(it.url, it.name) },
    )
}

@Composable
private fun HomeScreen(
    state: HomeState,
    onClick: (Data.Result) -> Unit,
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
                text = "Pokemon list",
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

            LoadState.Loading ->
                items(20) {
                    Shimmer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        cornerRadius = 12.dp,
                    )
                }


            is LoadState.NotLoading ->
                items(pagingData.itemCount) {
                    PokemonCard(
                        pokemon = pagingData[it] ?: return@items,
                        onClick = onClick
                    )
                }
        }
    }
}


@Composable
fun PokemonCard(
    pokemon: Data.Result,
    onClick: (Data.Result) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onClick(pokemon) },
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(pokemon.url)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        )
        Text(
            text = pokemon.name,
            modifier = Modifier
                .padding(8.dp)
        )
    }
}