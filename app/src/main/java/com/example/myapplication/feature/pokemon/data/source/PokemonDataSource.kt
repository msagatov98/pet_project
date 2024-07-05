package com.example.myapplication.feature.pokemon.data.source

import com.example.myapplication.feature.pokemon.data.model.Data
import com.example.myapplication.feature.pokemon.data.model.PokemonDetail
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

class PokemonDataSource(
    private val httpClient: HttpClient,
) {

    suspend fun getData(page: Int): Data {
        return httpClient.get {
            url {
                path("api/v2/pokemon")
                parameter("offset", page)
            }
        }.body<Data>()
    }

    suspend fun getPokemonDetails(name: String): PokemonDetail {
        return httpClient.get {
            url {
                path("api/v2/pokemon/$name")
            }
        }.body<PokemonDetail>()
    }
}