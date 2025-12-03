package com.mobile.vedroid.kt.storage

import com.mobile.vedroid.kt.model.JokeAdapterModel
import kotlinx.coroutines.flow.Flow

interface OfflineStorage {
    fun load () : Flow<List<JokeAdapterModel>>
    suspend fun save (items: List<JokeAdapterModel>)
}