package com.example.myapplication.core.android.ui.presentation.component

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun Shimmer(modifier: Modifier, cornerRadius: Dp = 16.dp) {
    Box(modifier = modifier.shimmerEffect(cornerRadius))
}

private fun Modifier.shimmerEffect(
    cornerRadius: Dp,
): Modifier = composed {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        label = "",
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                MaterialTheme.colorScheme.outlineVariant,
                MaterialTheme.colorScheme.outline,
                MaterialTheme.colorScheme.outlineVariant,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        ),
        shape = RoundedCornerShape(cornerRadius)
    ).onGloballyPositioned { size = it.size }
}

@Composable
@ComponentPreview
private fun ShimmerPreview() {
    Column {
        AppThemePreview {
            Shimmer(
                modifier = Modifier
                    .padding(16.dp)
                    .size(200.dp),
            )
        }
        Spacer(size = 24.dp)
        AppThemePreview(isDarkTheme = true) {
            Shimmer(
                modifier = Modifier
                    .padding(16.dp)
                    .size(200.dp)
            )
        }
    }
}