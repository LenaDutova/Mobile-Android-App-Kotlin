package com.mobile.vedroid.kt.adapter

interface ExpandableAdapter <T> {
    fun addItems(newJokes: List<T>) : Int
}