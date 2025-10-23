package com.mobile.vedroid.kt.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/*
{
    "id": 16,
    "type": "programming",
    "setup": "What's the object-oriented way to become wealthy?",
    "punchline": "Inheritance"
}
 */
@Serializable
data class DenoJoke(val id: Int, val type: String, val setup: String, @SerialName("punchline") val delivery: String){
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DenoJoke

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}
