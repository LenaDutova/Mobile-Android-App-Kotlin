package com.mobile.vedroid.kt

import android.app.Application
import android.content.Context
import android.os.LocaleList
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.mobile.vedroid.kt.storage.SettingsDSStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MobileApplication : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()

        val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

        // read old mode & checkboxRuLanguage
        applicationScope.launch () {
            val ds = SettingsDSStore()
            val mode = ds.loadLightOrNightMode().first()
            if (mode != AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM) AppCompatDelegate.setDefaultNightMode(mode)
            setLocaleAlwaysRu(ds.isAlwaysRuLanguage().first())
        }
    }



    companion object {
        private var instance: MobileApplication? = null
        fun mobileApplicationContext() : Context{
            return instance!!.applicationContext
        }

        fun setLocaleAlwaysRu(alwaysRu: Boolean) {
            if (alwaysRu && AppCompatDelegate.getApplicationLocales().size() == 0){
                Log.d("TAG_MobileApplication", "Set always RU language")
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ru"))
            } else if (!alwaysRu && AppCompatDelegate.getApplicationLocales().size() != 0){
                Log.d("TAG_MobileApplication", "NOT need Ru, set default language")
                AppCompatDelegate.setApplicationLocales(LocaleListCompat.getEmptyLocaleList())
            }
        }
    }

}