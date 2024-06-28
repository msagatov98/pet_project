package com.example.myapplication.feature.pokemon.data.repository

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.myapplication.feature.pokemon.data.model.Pokemon
import com.example.myapplication.feature.pokemon.data.model.RemoteKeys

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Pokemon>)

    @Query("Select * From pokemons Order By page")
    fun getPokemon(): PagingSource<Int, Pokemon>

    @Query("Delete From pokemons")
    suspend fun clearAllPokemon()
}

@Dao
interface RemoteKeysDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<RemoteKeys>)

    @Query("Select * From remote_key Where pokemon_id = :id")
    suspend fun getRemoteKeyByMovieID(id: String): RemoteKeys?

    @Query("Delete From remote_key")
    suspend fun clearRemoteKeys()

    @Query("Select created_at From remote_key Order By created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}

@Database(
    entities = [Pokemon::class, RemoteKeys::class],
    version = 1,
)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
    abstract fun getRemoteKeysDao(): RemoteKeysDao
}
