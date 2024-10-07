package com.example.myapplication.feature.pokemon.data.mapper

import com.example.myapplication.feature.pokemon.data.model.Data
import com.example.myapplication.feature.pokemon.data.model.Pokemon
import com.example.myapplication.feature.pokemon.data.model.PokemonDetail
import com.example.myapplication.feature.pokemon.presentation.model.Stat
import java.util.UUID

fun map(from: Data, page: Int): List<Pokemon> {
    return from.results.map {
        val number = if (it.url.endsWith("/")) {
            it.url.dropLast(1).takeLastWhile { it.isDigit() }
        } else {
            it.url.takeLastWhile { it.isDigit() }
        }
        val url =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"

        Pokemon(
            id = UUID.randomUUID().toString(),
            name = it.name,
            imageUrl = url,
            page = page
        )
    }
}

fun mapPokemonDetailToStatList(from: PokemonDetail): List<Stat> {
    return from.stats.map { Stat(it.baseStat, it.stat.name) }
}
