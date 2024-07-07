package com.example.myapplication.feature.pokemon.presentation.screen.detail

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun StateIndicator(
    modifier: Modifier = Modifier,
    name: String,
    value: Int,
) {
    Box(modifier = modifier) {
        var progress by remember { mutableStateOf(0.1f) }
        val animatedProgress by animateFloatAsState(
            label = "",
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
