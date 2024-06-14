package com.example.myapplication.feature.home.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.common.network.Resource

class PokemonPagingSource(
    private val homeRepository: HomeRepository
) : PagingSource<Int, Data.Result>() {

    private var page = 0

    override fun getRefreshKey(state: PagingState<Int, Data.Result>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data.Result> {
        return when (val result = homeRepository.getData((page++) * 20)) {
            is Resource.Error -> LoadResult.Error(result.exception)
            Resource.Loading -> LoadResult.Invalid()
            is Resource.Success -> {
                val data = result.data
                LoadResult.Page(
                    data = data.results,
                    prevKey = null,
                    nextKey = if (data.next == null) null else page,
                )
            }
        }
    }
}