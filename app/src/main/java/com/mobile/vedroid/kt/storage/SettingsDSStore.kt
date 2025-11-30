package com.mobile.vedroid.kt.storage

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.mobile.vedroid.kt.MobileApplication
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DS")
class SettingsDSStore {

//    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "DS")

    suspend fun saveLightOrNightMode(mode: Int) {
        MobileApplication.mobileApplicationContext().dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[SETTINGS_MODE] = mode
            }
        }
    }

    suspend fun saveAlwaysRuLanguage(ru: Boolean) {
        MobileApplication.mobileApplicationContext().dataStore.updateData {
            it.toMutablePreferences().also { preferences ->
                preferences[SETTINGS_IS_ALWAYS_RU] = ru
            }
        }
    }

    fun loadLightOrNightMode(): Flow<Int> = MobileApplication.mobileApplicationContext().dataStore.data.map { preferences ->
        preferences[SETTINGS_MODE] ?: AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }

    fun isAlwaysRuLanguage(): Flow<Boolean> = MobileApplication.mobileApplicationContext().dataStore.data.map { preferences ->
        preferences[SETTINGS_IS_ALWAYS_RU] ?: false
    }

    companion object {
        val SETTINGS_MODE = intPreferencesKey("mode")
        val SETTINGS_IS_ALWAYS_RU = booleanPreferencesKey("ru")
    }
}