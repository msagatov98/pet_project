package com.example.myapplication.core.ui.component

import androidx.compose.foundation.pager.PagerState

val PagerState.isLast: Boolean
    get() = pageCount - 1 <= currentPage