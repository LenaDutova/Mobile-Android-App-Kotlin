package com.mobile.vedroid.kt.extensions

import com.mobile.vedroid.kt.model.JokeAdapterModel

fun JokeAdapterModel.valueOf() : String {
    val txt = StringBuilder()
    txt.append("id:")
    txt.append(getId())
    txt.append("\nsingle:")
    txt.append(isSingle())
    txt.append("\nsetup:")
    txt.append(getSetup())
    txt.append("\ndelivery:")
    txt.append(getDelivery())
    txt.append("\n")
    txt.append(javaClass.simpleName)
    txt.append("\n")

    return txt.toString()
}