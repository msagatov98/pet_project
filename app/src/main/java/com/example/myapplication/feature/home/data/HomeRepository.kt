package com.example.myapplication.feature.home.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myapplication.common.network.Resource
import com.example.myapplication.common.network.apiCall
import com.example.myapplication.common.network.map
import com.squareup.moshi.Json
import kotlinx.coroutines.flow.Flow
import java.util.UUID

class HomeRepository(
    private val homeApi: HomeApi,
    private val pokemonDatabase: PokemonDatabase,
) {

    suspend fun getData(page: Int): Resource<Data> {
        return apiCall { homeApi.getData(page) }
            .map { data ->
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

    suspend fun getPokemon(name: String): Resource<List<com.example.myapplication.feature.second.Stat>> {
        return apiCall { homeApi.getData(name) }
            .map {
                it.stats.map {
                    com.example.myapplication.feature.second.Stat(it.baseStat, it.stat.name)
                }
            }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getPokemonPagingSource(): Flow<PagingData<Pokemon>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { pokemonDatabase.getPokemonDao().getPokemon() },
            remoteMediator = PokemonRemoteMediator(
                homeRepository = this,
                pokemonDatabase = pokemonDatabase,
            )
        ).flow
    }
}

data class Data(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>,
) {
    data class Result(
        val name: String,
        val url: String,
    )
}

data class PokemonDetail(
    val stats: List<Stat>
)

data class Stat(
    @Json(name = "base_stat")
    val baseStat: Int,
    val stat: StateName
)

data class StateName(
    val name: String,
)