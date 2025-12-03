package com.mobile.vedroid.kt.model.entityes

import com.mobile.vedroid.kt.model.JokeAdapterModel

//@Entity
data class Joke (
    /*@PrimaryKey*/ private val id: Int,
    private val isSingle: Boolean,
    private val setup: String,
    private val delivery: String?
    ) : JokeAdapterModel {

    override fun getId(): Int = id
    override fun isSingle(): Boolean = isSingle
    override fun getSetup(): String = setup
    override fun getDelivery(): String? = delivery

}