package com.example.myapplication.core.ui.component

import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.LoadState

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
