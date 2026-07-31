package com.mobile.vedroid.kt.storage

import android.content.Context
import android.content.SharedPreferences
import com.mobile.vedroid.kt.MobileApplication
import com.mobile.vedroid.kt.model.Account
import androidx.core.content.edit

class AccountSPStore (){

    private val sp: SharedPreferences by lazy{
        val ctx = MobileApplication.mobileApplicationContext()
        ctx.getSharedPreferences("SP", Context.MODE_PRIVATE)
    }

    private var name: String?
        get () = sp.getString(ACCOUNT_NAME, null)
        set (value) {
            sp.edit { putString(ACCOUNT_NAME, value) }
        }

    private var sex: Boolean
        get () = sp.getBoolean(ACCOUNT_SEX, false)
        set (value) {
            sp.edit { putBoolean(ACCOUNT_SEX, value) }
        }

    private fun hasAccount (): Boolean = sp.contains(ACCOUNT_NAME)


    fun saveAccount (account: Account){
        name = account.login
        sex = account.gender
    }

    fun clearAccount(): Unit {
        sp.edit { clear() }
    }

    fun loadAccount (): Account? {
        return if (hasAccount()) Account (name!!, sex)
        else null
    }



    companion object SP{
        private const val ACCOUNT_NAME = "NAME"
        private const val ACCOUNT_SEX = "SEX"
    }
}