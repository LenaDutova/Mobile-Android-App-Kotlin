package com.mobile.vedroid.kt.storage.sqlite

import com.mobile.vedroid.kt.extensions.toJoke
import com.mobile.vedroid.kt.model.JokeAdapterModel
import com.mobile.vedroid.kt.storage.OfflineStorage
import kotlinx.coroutines.flow.Flow

class SQLiteManager : OfflineStorage {

    override fun load(): Flow<List<JokeAdapterModel>> = MobileDatabase.db.jokesDAO().getAllUsers()

    override suspend fun save(items: List<JokeAdapterModel>) {
        items.forEach {
            model -> MobileDatabase.db.jokesDAO().insertAll(model.toJoke())
        }
    }
}