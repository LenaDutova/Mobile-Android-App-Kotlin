package com.mobile.vedroid.kt.extensions

import android.util.Log
import androidx.fragment.app.Fragment
import com.mobile.vedroid.kt.BuildConfig

fun Fragment.debugging(message: String) {
    if (BuildConfig.DEBUG) Log.d("${TAG}_${javaClass.simpleName}", message)
}