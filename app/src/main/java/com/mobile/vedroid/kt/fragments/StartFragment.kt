package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account

class StartFragment : Fragment(R.layout.fragment_start) {

    lateinit var greeting : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        greeting = view.findViewById(R.id.tv_greeting)

        val btnFinal : Button = view.findViewById(R.id.btn_to_final)
        btnFinal.setOnClickListener {
            debugging("Click to final")
            findNavController().navigate(R.id.action_screen_start_to_final)
        }

        val btnReturning : Button = view.findViewById(R.id.btn_to_returning)
        btnReturning.setOnClickListener {
            debugging("Click to returning")
            findNavController().navigate(R.id.action_screen_start_to_register)
        }

        val args: StartFragmentArgs by navArgs()
        if (args.ACCOUNT != null) showGreeting(args.ACCOUNT!!)
        // if (!args.LOGIN.isNullOrBlank()) showGreeting(args.GENDER, args.LOGIN!!)
    }

    private fun showGreeting(user : Account) {
        var txt : String? = getString(R.string.text_greeting) + " "
        txt += if (user.gender) getString(R.string.text_mr) else getString(R.string.text_mrs)
        txt += " " + user.login + "!"
        debugging("Greeting: $txt")

        greeting.text = txt
    }

    private fun showGreeting(gender : Boolean, login: String){
        var txt : String? = getString(R.string.text_greeting) + " "
        txt += if (gender) getString(R.string.text_mr) else getString(R.string.text_mrs)
        txt += " $login!"
        debugging("Greeting: $txt")

        greeting.text = txt
    }

    private fun showGreeting(bundle: Bundle){
        var txt : String? = getString(R.string.text_greeting) + " "
        txt += if (bundle.getBoolean(SingleActivity.Companion.GENDER, false)) getString(R.string.text_mr) else getString(R.string.text_mrs)
        txt += " " + bundle.getString(SingleActivity.Companion.LOGIN) + "!"
        debugging("Greeting: $txt")

        greeting.text = txt
    }
}