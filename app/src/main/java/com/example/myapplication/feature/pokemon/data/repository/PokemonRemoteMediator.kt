package com.example.myapplication.feature.pokemon.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.feature.pokemon.data.model.Pokemon
import com.example.myapplication.feature.pokemon.data.model.RemoteKeys
import com.example.myapplication.feature.pokemon.data.source.local.PokemonDatabase
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PokemonRemoteMediator(
    private val pokemonRepository: PokemonRepository,
    private val pokemonDatabase: PokemonDatabase,
) : RemoteMediator<Int, Pokemon>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val creationTime = pokemonDatabase.getRemoteKeysDao().getCreationTime() ?: 0
        return if (System.currentTimeMillis() - creationTime < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, Pokemon>
    ): MediatorResult {

        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return when (val dataResource = pokemonRepository.getData(page)) {
            is Resource.Error -> MediatorResult.Error(dataResource.exception)
            Resource.Loading -> MediatorResult.Success(endOfPaginationReached = true)
            is Resource.Success -> {
                val pokemonList = dataResource.data

                val endOfPaginationReached = pokemonList.isEmpty()

                pokemonDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        pokemonDatabase.getPokemonDao().clearAllPokemon()
                        pokemonDatabase.getRemoteKeysDao().clearRemoteKeys()
                    }

                    val prevKey = if (page > 20) page - 20 else null
                    val nextKey = if (endOfPaginationReached) null else page + 20

                    val remoteKeys = pokemonList.map {
                        RemoteKeys(
                            movieID = it.id,
                            nextKey = nextKey,
                            prevKey = prevKey,
                            currentPage = page,
                        )
                    }


                    pokemonDatabase.getRemoteKeysDao().insertAll(remoteKeys)
                    pokemonDatabase.getPokemonDao()
                        .insertAll(pokemonList.map { it.copy(page = page) })
                }

                MediatorResult.Success(endOfPaginationReached)
            }
        }
    }


    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                pokemonDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { movie ->
            pokemonDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Pokemon>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { movie ->
            pokemonDatabase.getRemoteKeysDao().getRemoteKeyByMovieID(movie.id)
        }
    }
}