package com.example.myapplication.feature.home.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface HomeApi {

    @GET("pokemon")
    suspend fun getData(
        @Query("offset") offset: Int,
    ): Data

    @GET("pokemon/{name}")
    suspend fun getData(
        @Path("name") name: String
    ): PokemonDetail
}