package com.example.myapplication.core.ui.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun <T> HandleEffect(effect: Flow<T>, block: (T) -> Unit) {
    LaunchedEffect(Unit) {
        effect.collectLatest(block)
    }
}