package com.example.myapplication.core.android.ui.presentation.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

private const val ANIMATION_DURATION_MILLIS = 300
private const val ANIMATION_ARROW_ROTATION_TOP_ANGLE = 180f
private const val ANIMATION_ARROW_ROTATION_BOTTOM_ANGLE = 0f

@Composable
fun Selector(
    text: String,
    options: List<String>,
    onSelect: (String) -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val arrowRotationDegree by animateFloatAsState(
        label = "",
        animationSpec = tween(ANIMATION_DURATION_MILLIS),
        targetValue = if (expanded) {
            ANIMATION_ARROW_ROTATION_TOP_ANGLE
        } else {
            ANIMATION_ARROW_ROTATION_BOTTOM_ANGLE
        }
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .rippleClickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = text)
            Icon(
                modifier = Modifier.rotate(arrowRotationDegree),
                imageVector = Icons.Default.KeyboardArrowDown,
                contentDescription = null,
            )
        }
        DropdownMenu(
            expanded = expanded,
            offset = DpOffset(x = (-1).dp, y = 0.dp),
            onDismissRequest = { expanded = false },
        ) {
            repeat(options.size) {
                DropdownMenuItem(
                    onClick = { onSelect(options[it]) },
                    text = { Text(text = options[it]) },
                    modifier = Modifier.align(Alignment.End),
                )
            }
        }
    }
}

@Composable
@ComponentPreview
private fun SelectorPreview() {
    Column {
        AppThemePreview {
            Selector(
                text = "example",
                options = emptyList(),
                onSelect = { },
            )
        }
        Spacer(size = 16.dp)
        AppThemePreview(isDarkTheme = true) {
            Selector(
                text = "example",
                options = emptyList(),
                onSelect = { },
            )
        }
    }
}