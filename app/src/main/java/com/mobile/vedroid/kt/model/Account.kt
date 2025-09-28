package com.mobile.vedroid.kt.model

import java.io.Serializable

data class Account (val login: String, val gender: Boolean = false) : Serializable {}