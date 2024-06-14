package com.example.myapplication.common.ui.presentation.component

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Spacer(size: Dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(size))
}

@Composable
fun Spacer(width: Dp = 0.dp, height: Dp = 0.dp) {
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(width, height))
}