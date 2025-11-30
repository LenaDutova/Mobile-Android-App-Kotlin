package com.mobile.vedroid.kt

import android.app.Application
import android.content.Context


class MobileApplication : Application() {

    companion object {
        private var instance: MobileApplication? = null
        fun mobileApplicationContext() : Context{
            return instance!!.applicationContext
        }
    }

    init{
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
    }
}