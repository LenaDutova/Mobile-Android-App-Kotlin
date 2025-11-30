package com.mobile.vedroid.kt.model.requests

import com.mobile.vedroid.kt.model.JokeAdapterModel
import kotlinx.serialization.Serializable

/*
 jokes: [
     {
        "category": "Programming",
        "type": "single",
        "joke": "Programming is 10% science, 20% ingenuity, and 70% getting the ingenuity to work with the science.",
        "flags": {
            "nsfw": false,
            "religious": false,
            "political": false,
            "racist": false,
            "sexist": false,
            "explicit": false
        },
        "id": 37,
        "safe": true,
        "lang": "en"
    },
    {
        "category": "Programming",
        "type": "twopart",
        "setup": "What is a dying programmer's last program?",
        "delivery": "Goodbye, world!",
        "flags": {
            "nsfw": false,
            "religious": false,
            "political": false,
            "racist": false,
            "sexist": false,
            "explicit": false
        },
        "id": 55,
        "safe": true,
        "lang": "en"
    }
]
 */
@Serializable
data class ApiJoke(
        private val id: Int,
        private val type: String,
        private val joke: String?,
        private val setup: String?,
        private val delivery: String?) : JokeAdapterModel {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiJoke

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    // region // Pattern-Adapter

    override fun getId(): Int = id

    override fun isSingle(): Boolean = (type == "single")

    override fun getSetup(): String = if (isSingle()) joke!! else setup!!

    override fun getDelivery(): String? = delivery

    // endregion
}

@Serializable
data class ApiJokesList (var jokes: List<ApiJoke> = listOf<ApiJoke>())