package com.mobile.vedroid.kt.network

import android.util.Log
import com.mobile.vedroid.kt.model.ApiJoke
import com.mobile.vedroid.kt.model.ApiJokesList
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.URLProtocol
import io.ktor.http.appendPathSegments
import io.ktor.http.encodedPath
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

/*
doc: https://jokeapi.dev/
examples:
https://v2.jokeapi.dev/joke/Programming?amount=10
https://v2.jokeapi.dev/joke/Programming?type=single&amount=10
https://v2.jokeapi.dev/joke/Programming?type=twopart&amount=10
 */
interface ApiJokeService : KtorService {
    suspend fun getJokes(): List<ApiJoke>
    suspend fun getJokes(count: Int): List<ApiJoke>
}

object ApiKtorClient : ApiJokeService {

    private val client: HttpClient by lazy {
        HttpClient(Android){
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "v2.jokeapi.dev"
                    path("joke/")
                }
            }

            // to read json
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    prettyPrint = true
                    explicitNulls = false       // or joke, or type-setup absent in json
                    ignoreUnknownKeys = true    // to ignore another attrs in json
                })
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 3)
                exponentialDelay()
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.HEADERS
            }
        }
    }

    override suspend fun getJokes(): List<ApiJoke> {
        return withContext(Dispatchers.IO) {
            val response: HttpResponse = client.request { // client.get ("Programming?amount=5")
                method = HttpMethod.Get
                url {
                    path("Programming")
                    parameter("amount", 5)
                }
            }

            Log.d("TAG_ApiKtorClient", "loading in ${Thread.currentThread().name} request $response")
//            Log.d("TAG_ApiKtorClient", "data ${response.bodyAsText()}")

            (response.body() as ApiJokesList).jokes
        }
    }

    override suspend fun getJokes(count: Int): List<ApiJoke> {
        return withContext(Dispatchers.IO) {
            val response: HttpResponse = client.get ("Programming?amount=$count")
            response.body<ApiJokesList>().jokes
        }
    }

    override fun close() {
        client?.close()
    }

}