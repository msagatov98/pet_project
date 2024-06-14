package com.example.myapplication.feature.second

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.myapplication.common.ui.presentation.screen.Screen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.secondScreen() {
    composable<Screen.Second> {
        val second = it.toRoute<Screen.Second>()
        val viewModel = koinViewModel<SecondViewModel> {
            parametersOf(second.name)
        }
        val stats by viewModel.list.collectAsStateWithLifecycle()
        SecondScreen(
            url = second.imageUrl,
            name = second.name,
            stats = stats,
        )
    }
}

@Composable
fun SecondScreen(
    url: String,
    name: String,
    stats: List<Stat>,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Text(
            text = name,
            modifier = Modifier
                .padding(8.dp)
        )

        stats.forEach {
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

@Composable
fun StateIndicator(
    modifier: Modifier = Modifier,
    name: String,
    value: Int,
) {
    Box(modifier = modifier) {
        LinearProgressIndicator(
            progress = { value / 100F },
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(28.dp)
                .clip(RoundedCornerShape(16.dp))
        )

        Text(
            text = name,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 8.dp),
            color = MaterialTheme.colorScheme.onPrimary,
        )

        Text(
            text = "$value",
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp),
            color = if (value >= 100) {
                MaterialTheme.colorScheme.onPrimary
            } else {
                MaterialTheme.colorScheme.onSurfaceVariant
            },
        )
    }
}
