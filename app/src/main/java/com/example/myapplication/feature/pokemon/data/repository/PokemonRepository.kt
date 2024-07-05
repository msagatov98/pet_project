package com.example.myapplication.feature.pokemon.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.core.resource.apiCall
import com.example.myapplication.core.resource.map
import com.example.myapplication.feature.pokemon.data.mapper.DataToPokemonMapper
import com.example.myapplication.feature.pokemon.data.model.Pokemon
import com.example.myapplication.feature.pokemon.data.source.PokemonDataSource
import com.example.myapplication.feature.pokemon.presentation.model.Stat
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val mapper: DataToPokemonMapper,
    private val pokemonDatabase: PokemonDatabase,
    private val pokemonDataSource: PokemonDataSource,
) {

    suspend fun getData(page: Int): Resource<List<Pokemon>> {
        return apiCall { pokemonDataSource.getData(page) }
            .map { data -> mapper.map(from = data, page = page) }
    }

    suspend fun getPokemon(name: String): Resource<List<Stat>> {
        return apiCall { pokemonDataSource.getPokemonDetails(name) }.map {
            it.stats.map {
                Stat(it.baseStat, it.stat.name)
            }
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPokemonPagingSource(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pokemonDatabase.getPokemonDao().getPokemon() },
            remoteMediator = PokemonRemoteMediator(
                pokemonRepository = this,
                pokemonDatabase = pokemonDatabase,
            )
        ).flow
    }
}
