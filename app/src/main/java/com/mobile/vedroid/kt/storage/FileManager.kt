package com.mobile.vedroid.kt.storage

import android.content.Context
import android.util.Log
import com.mobile.vedroid.kt.MobileApplication
import com.mobile.vedroid.kt.extensions.valueOf
import com.mobile.vedroid.kt.model.JokeAdapterModel
import com.mobile.vedroid.kt.model.entityes.JokeFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

class FileManager : OfflineStorage {

    override fun load(): Flow<List<JokeAdapterModel>> = flow {
        val jokes = mutableListOf<JokeAdapterModel>()

        if (File(MobileApplication.mobileApplicationContext().getFilesDir(), FILENAME).exists()) {
            try {
                File(
                    MobileApplication.mobileApplicationContext().getFilesDir(),
                    FILENAME
                ).forEachLine {
                    if (!JokeFactory.parseToJokeAttr(it)) jokes.add(JokeFactory.create())
                }
            } catch (e: IOException) {
                Log.e("TAG_${javaClass.getSimpleName()}", e.message, e)
            }
        }
        emit(jokes)
    }.flowOn(Dispatchers.IO)

    override suspend fun save(items: List<JokeAdapterModel>) {
        try {
            MobileApplication.mobileApplicationContext()
                .openFileOutput(FILENAME, Context.MODE_APPEND)
                .use { fos: FileOutputStream ->
                    items.forEach { joke ->
                        fos.write(joke.valueOf().toByteArray())
                    }
            }
        } catch (e: FileNotFoundException){
            Log.e("TAG_${javaClass.getSimpleName()}", e.message, e)
        } catch (e: IOException){
            Log.e("TAG_${javaClass.getSimpleName()}", e.message, e)
        }
    }



    companion object {
        val FILENAME : String = "backup.txt"
    }
}