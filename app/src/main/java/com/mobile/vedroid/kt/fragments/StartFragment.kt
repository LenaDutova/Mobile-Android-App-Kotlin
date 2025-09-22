package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
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
            (activity as SingleActivity).navigate(SingleActivity.Companion.JUMP_TO_FINAL)
        }

        val btnReturning : Button = view.findViewById(R.id.btn_to_returning)
        btnReturning.setOnClickListener {
            activity?.debugging("Click to returning")
            (activity as SingleActivity).navigate(SingleActivity.Companion.JUMP_TO_RETURNING)
        }

        if (arguments != null && requireArguments().containsKey(SingleActivity.Companion.LOGIN)){
            var txt : String? = "Welcome, "
            txt += if (requireArguments().getBoolean(SingleActivity.Companion.GENDER, false)) "Mr. " else "Mrs. "
            txt += requireArguments().getString(SingleActivity.Companion.LOGIN) + "!"

            val greeting : TextView = view.findViewById(R.id.tv_greeting)
            greeting.text = txt
        }
    }
}