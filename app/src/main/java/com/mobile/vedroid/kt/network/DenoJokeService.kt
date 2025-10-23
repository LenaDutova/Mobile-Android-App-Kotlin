package com.mobile.vedroid.kt.network

import android.util.Log
import com.mobile.vedroid.kt.model.DenoJoke
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/*
doc: https://github.com/UltiRequiem/joke-api
examples: https://joke.deno.dev/type/programming/10
 */

interface DenoJokeService: KtorService  {
    suspend fun getJokes(): List<DenoJoke>
    suspend fun getJokes(count: Int): List<DenoJoke>
}

public object DenoKtorClient: DenoJokeService {

    private val client: HttpClient by lazy {
        HttpClient(Android){
            defaultRequest {
                url("https://joke.deno.dev/")
            }

            // to read json
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = 3)
                exponentialDelay()
//                retryOnExceptionIf { request, cause ->
//                    cause is NetworkError
//                }
            }
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.HEADERS
//            }
        }
    }

    override fun close() {
        client?.close()
    }


    override suspend fun getJokes(): List<DenoJoke> {
        val response: HttpResponse = client.get {
            url {
                protocol = URLProtocol.HTTPS
                path("type/programming/10")
            }
        }

//            client.get("type/programming/10")

        Log.d ("TAG_DenoJokeService", "request ${response}")
        Log.d ("TAG_DenoJokeService", "date ${response.bodyAsText()}")

        return response.body()
    }

    override suspend fun getJokes(count: Int): List<DenoJoke> {
        return client.get("https://joke.deno.dev/type/programming/${count}").body()
    }
}