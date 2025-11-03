package com.mobile.vedroid.kt.extensions

import android.app.Activity
import android.util.Log
import com.mobile.vedroid.kt.BuildConfig

const val TAG: String = "TAG"

fun Activity.debugging(message: String) {
    if (BuildConfig.DEBUG) Log.d("${TAG}_${localClassName}", message)
}