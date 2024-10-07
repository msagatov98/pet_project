package com.example.myapplication.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.path

suspend inline fun <reified T> HttpClient.get(
    path: String,
    parameters: Map<String, Any> = emptyMap(),
): T {
    return get {
        url {
            path(path)
            parameters.forEach { (key, value) ->
                parameter(key, value)
            }
        }
    }.body<T>()
}