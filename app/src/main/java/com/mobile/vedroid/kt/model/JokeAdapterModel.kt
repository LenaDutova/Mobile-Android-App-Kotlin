package com.mobile.vedroid.kt.model

interface JokeAdapterModel {
    fun getId(): Int

    fun isSingle(): Boolean
    fun getSetup(): String
    fun getDelivery(): String?
}