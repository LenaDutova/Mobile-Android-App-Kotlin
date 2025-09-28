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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        val btnFinal : Button = view.findViewById(R.id.btn_to_final)
        btnFinal.setOnClickListener {
            activity?.debugging("Click to final")
            findNavController().navigate(R.id.action_screen_start_to_final)
        }

        val btnReturning : Button = view.findViewById(R.id.btn_to_returning)
        btnReturning.setOnClickListener {
            activity?.debugging("Click to returning")
            findNavController().navigate(R.id.action_screen_start_to_register)
        }

        if (arguments != null && !arguments?.getString(SingleActivity.Companion.LOGIN).isNullOrEmpty()){
            var txt : String? = "Welcome, "
            txt += if (arguments?.getBoolean(SingleActivity.Companion.GENDER) ?: false) "Mr. " else "Mrs. "
            txt += arguments?.getString(SingleActivity.Companion.LOGIN) + "!"

            val greeting : TextView = view.findViewById(R.id.tv_greeting)
            greeting.text = txt
        }
    }
}