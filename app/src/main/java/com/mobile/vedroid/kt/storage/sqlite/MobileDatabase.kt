package com.mobile.vedroid.kt.storage.sqlite

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mobile.vedroid.kt.MobileApplication
import com.mobile.vedroid.kt.model.entityes.Joke

@Database(entities = [Joke::class], version = 1)
abstract class MobileDatabase : RoomDatabase()
{
    abstract fun jokesDAO() : JokesDAO



    companion object {
        val db = Room.databaseBuilder(
            MobileApplication.mobileApplicationContext(),
            MobileDatabase::class.java, "DB"
        ).build()
    }
}