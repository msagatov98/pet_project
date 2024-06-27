package com.example.myapplication.feature.home.presentation

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.example.myapplication.feature.home.data.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

@Immutable
data class HomeState(
    val pokemonPagingSource: Flow<PagingData<Pokemon>> = flow { PagingData.empty<Data.Result>() },
)
