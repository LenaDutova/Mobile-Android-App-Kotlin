package com.mobile.vedroid.kt.storage

import android.content.Context
import android.content.SharedPreferences
import com.mobile.vedroid.kt.MobileApplication
import com.mobile.vedroid.kt.model.Account

class AccountSPStore (){

    private val sp: SharedPreferences by lazy{
        val ctx = MobileApplication.mobileApplicationContext()
        ctx.getSharedPreferences("SP", Context.MODE_PRIVATE)
    }

    private var name: String?
        get () = sp.getString(ACCOUNT_NAME, null)
        set (value) {
            sp.edit().putString(ACCOUNT_NAME, value).apply()
        }

    private var sex: Boolean
        get () = sp.getBoolean(ACCOUNT_SEX, false)
        set (value) {
            sp.edit().putBoolean(ACCOUNT_SEX, value).apply()
        }

    private fun hasAccount (): Boolean = sp.contains(ACCOUNT_NAME)


    fun saveAccount (account: Account){
        name = account.login
        sex = account.gender
    }

    fun clearAccount(): Unit {
        sp.edit().clear().apply()
    }

    fun loadAccount (): Account? {
        if (hasAccount()) return Account (name!!, sex)
        else return null
    }



    companion object SP{
        private const val ACCOUNT_NAME = "NAME"
        private const val ACCOUNT_SEX = "SEX"
    }
}