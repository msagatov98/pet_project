package com.example.myapplication.feature.home.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<Result>,
) {
    @Serializable
    data class Result(
        val name: String,
        val url: String,
    )
}

@Serializable
data class PokemonDetail(
    val stats: List<Stat>
)

@Serializable
data class Stat(
    @SerialName("base_stat")
    val baseStat: Int,
    val stat: StateName
)

@Serializable
data class StateName(
    val name: String,
)