package com.example.myapplication.feature.pokemon.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.core.resource.Resource
import com.example.myapplication.core.resource.apiCall
import com.example.myapplication.core.resource.map
import com.example.myapplication.feature.pokemon.data.model.Data
import com.example.myapplication.feature.pokemon.data.model.Pokemon
import com.example.myapplication.feature.pokemon.data.model.PokemonDetail
import com.example.myapplication.feature.pokemon.presentation.model.Stat
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path
import kotlinx.coroutines.flow.Flow

class PokemonRepository(
    private val pokemonDatabase: PokemonDatabase,
    private val httpClient: HttpClient,
) {

    suspend fun getData(page: Int): Resource<Data> {
        return apiCall {
            httpClient.get {
                url {
                    path("api/v2/pokemon")
                    parameter("offset", page)
                }
            }.body<Data>()
        }.map { data ->
            val pokemons = data.results.map {
                val number = if (it.url.endsWith("/")) {
                    it.url.dropLast(1).takeLastWhile { it.isDigit() }
                } else {
                    it.url.takeLastWhile { it.isDigit() }
                }
                val url =
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                it.copy(url = url)
            }

            data.copy(results = pokemons)
        }
    }

    suspend fun getPokemon(name: String): Resource<List<Stat>> {
        return apiCall {
            httpClient.get {
                url {
                    path("api/v2/pokemon/$name")
                }
            }.body<PokemonDetail>()
        }.map {
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
