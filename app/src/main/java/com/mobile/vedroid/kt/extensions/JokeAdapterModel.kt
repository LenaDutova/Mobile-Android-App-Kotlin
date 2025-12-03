package com.mobile.vedroid.kt.extensions

import com.mobile.vedroid.kt.model.JokeAdapterModel
import com.mobile.vedroid.kt.model.entityes.Joke

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

fun JokeAdapterModel.toJoke() = Joke(getId(), isSingle(), getSetup(), getDelivery())

fun JokeAdapterModel.equals(other: Any?): Boolean {
    if (this === other) return true
    if (other is JokeAdapterModel)  return getId() == other.getId()
    else return false
}

fun JokeAdapterModel.hashCode(): Int {
    return getId()
}