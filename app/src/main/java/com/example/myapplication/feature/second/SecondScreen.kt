package com.example.myapplication.feature.second

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import kotlinx.coroutines.delay
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
        var progress by remember { mutableStateOf(0.1f) }
        val animatedProgress by animateFloatAsState(
            targetValue = progress,
            animationSpec = ProgressIndicatorDefaults.ProgressAnimationSpec
        )
        LinearProgressIndicator(
            progress = { animatedProgress },
            gapSize = 0.dp,
            strokeCap = ProgressIndicatorDefaults.CircularDeterminateStrokeCap,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .height(28.dp)
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

        LaunchedEffect(key1 = Unit) {
            delay(200)
            progress = value / 100F
        }
    }
}
