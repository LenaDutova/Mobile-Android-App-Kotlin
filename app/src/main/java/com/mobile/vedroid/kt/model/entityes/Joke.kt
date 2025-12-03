package com.mobile.vedroid.kt.model.entityes

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mobile.vedroid.kt.model.JokeAdapterModel

@Entity (tableName = "jokes")
data class Joke (
    @PrimaryKey private val id: Int,
    private val isSingle: Boolean,
    private val setup: String,
    private val delivery: String?
    ) : JokeAdapterModel {

    override fun getId(): Int = id
    override fun isSingle(): Boolean = isSingle
    override fun getSetup(): String = setup
    override fun getDelivery(): String? = delivery
}