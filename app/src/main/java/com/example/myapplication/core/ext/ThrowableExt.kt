package com.example.myapplication.core.ext

val Throwable.errorMessage: String
    get() = localizedMessage ?: message ?: toString()