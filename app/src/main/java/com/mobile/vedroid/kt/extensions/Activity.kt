package com.mobile.vedroid.kt.extensions

import android.app.Activity
import android.util.Log

const val DEBUG: Boolean = true
const val TAG: String = "TAG"

fun Activity.debugging(message: String) {
    if (DEBUG) Log.d("${TAG}_${localClassName}", message)
}