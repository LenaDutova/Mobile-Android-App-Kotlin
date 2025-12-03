package com.mobile.vedroid.kt.storage.sqlite

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mobile.vedroid.kt.model.entityes.Joke
import kotlinx.coroutines.flow.Flow

@Dao
interface JokesDAO {
    @Query("SELECT * FROM jokes")
    fun getAllUsers(): Flow<List<Joke>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(joke: Joke)

    @Delete
    suspend fun deleteAll(jokes: List<Joke>)
}