package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.extensions.debugging

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

        if (arguments != null && !arguments?.getString(SingleActivity.Companion.LOGIN).isNullOrEmpty()){
            showGreeting(requireArguments())
        }
    }

    private fun showGreeting(bundle: Bundle){
        var txt : String? = getString(R.string.text_greeting) + " "
        txt += if (bundle.getBoolean(SingleActivity.Companion.GENDER, false)) getString(R.string.text_mr) else getString(R.string.text_mrs)
        txt += " " + bundle.getString(SingleActivity.Companion.LOGIN) + "!"
        debugging("Greeting: $txt")

        greeting.text = txt
    }
}