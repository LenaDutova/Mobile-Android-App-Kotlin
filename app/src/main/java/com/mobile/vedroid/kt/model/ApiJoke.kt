package com.mobile.vedroid.kt.model

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
data class ApiJoke(val id: Int, val type: String, val joke: String?, val setup: String?, val delivery: String?){

    fun isTypeSingle () = (type == "single")


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiJoke

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}

@Serializable
data class ApiJokesList (var jokes: List<ApiJoke> = listOf<ApiJoke>())