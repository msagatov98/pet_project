package com.example.myapplication.core.android.ui.presentation.component

import androidx.compose.foundation.pager.PagerState

val PagerState.isLast: Boolean
    get() = pageCount - 1 <= currentPage