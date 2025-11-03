package com.mobile.vedroid.kt.network

import android.util.Log
import com.mobile.vedroid.kt.model.DenoJoke
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
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
object DenoKtorClient: JokeService<DenoJoke> {

    private val client: HttpClient by lazy {
        HttpClient(Android){
            // to read json
            install(ContentNegotiation) {
                json(Json {
                    isLenient = true
                    prettyPrint = true
                })
            }
        }
    }

    override fun close() {
        client.close()
    }

    override suspend fun getJokes(): List<DenoJoke> {
        val response: HttpResponse = client.get { // client.get("type/programming/5")
            url {
                protocol = URLProtocol.HTTPS
                host = "joke.deno.dev"
                path("type", "programming", "5")
            }
        }

        Log.d ("TAG_DenoJokeService", "loading in ${Thread.currentThread().name} request $response")
        Log.d ("TAG_DenoJokeService", "data ${response.bodyAsText()}")

        return response.body<List<DenoJoke>>()
    }

    override suspend fun getJokes(count: Int): List<DenoJoke> {
        return client.get("https://joke.deno.dev/type/programming/${count}").body()
    }
}