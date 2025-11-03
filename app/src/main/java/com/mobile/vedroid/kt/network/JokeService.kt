package com.mobile.vedroid.kt.network

import io.ktor.utils.io.core.Closeable

interface JokeService <T> : Closeable {
    suspend fun getJokes(): List<T>
    suspend fun getJokes(count: Int): List<T>
}