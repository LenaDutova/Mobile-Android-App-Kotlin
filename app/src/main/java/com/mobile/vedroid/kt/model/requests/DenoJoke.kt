package com.mobile.vedroid.kt.model.requests

import com.mobile.vedroid.kt.model.JokeAdapterModel
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
data class DenoJoke(
        private val id: Int,
        private val type: String,
        private val setup: String,
        private @SerialName("punchline") val delivery: String) : JokeAdapterModel{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as DenoJoke

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }

    // region // Pattern-Adapter

    override fun getId(): Int = id

    override fun isSingle(): Boolean = false

    override fun getSetup(): String = setup

    override fun getDelivery(): String = delivery

    // endregion
}