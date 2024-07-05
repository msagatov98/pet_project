package com.example.myapplication.core.resource

sealed interface Resource<out T> {
    data object Loading : Resource<Nothing>
    data class Success<out T>(val data: T) : Resource<T>
    data class Error(val exception: Exception) : Resource<Nothing>
}

suspend fun <T> apiCall(
    call: suspend () -> T,
): Resource<T> {
    return try {
        Resource.Success(call())
    } catch (e: Exception) {
        Resource.Error(e)
    }
}

fun <T : Any, R : Any> Resource<T>.map(mapper: (T) -> R): Resource<R> {
    return when (this) {
        Resource.Loading -> Resource.Loading
        is Resource.Error -> Resource.Error(this.exception)
        is Resource.Success -> Resource.Success(mapper(this.data))
    }
}