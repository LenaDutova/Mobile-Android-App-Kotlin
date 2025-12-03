package com.mobile.vedroid.kt.model.entityes

object JokeFactory {
    private var id : Int = -1
    private var setup : String? = null
    private var delivery : String? = null
    private var isSingle : Boolean = false

    fun create() : Joke = Joke(id, isSingle, setup!!, delivery)

    fun parseToJokeAttr(txt: String) : Boolean {
        if (txt.startsWith("id:")) {
            id = txt.replaceFirst("id:".toRegex(), "").toInt()
            return true
        }
        if (txt.startsWith("single:")) {
            isSingle = txt.replaceFirst("single:".toRegex(), "").toBoolean()
            return true
        }
        if (txt.startsWith("setup:")) {
            setup = txt.replaceFirst("setup:".toRegex(), "")
            return true
        }
        if (txt.startsWith("delivery:")) {
            delivery = txt.replaceFirst("delivery:".toRegex(), "")
            return true
        }
        return false
    }
}