package com.example.myapplication.common.ui.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

@Composable
fun Modifier.rippleClickable(
    onClick: () -> Unit,
): Modifier = composed {
    then(
       Modifier.clickable(
           interactionSource = remember { MutableInteractionSource() },
           indication = ripple(),
           onClick = onClick,
       )
    )
}