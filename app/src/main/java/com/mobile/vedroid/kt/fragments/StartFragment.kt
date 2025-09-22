package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.fragment.app.setFragmentResultListener
import com.google.android.material.textfield.TextInputEditText
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
            parentFragmentManager.commit {
                replace<FinalFragment>(R.id.main)
            }
        }

        val btnReturning : Button = view.findViewById(R.id.btn_to_returning)
        btnReturning.setOnClickListener {
            debugging("Click to returning")
            parentFragmentManager.commit {
                replace(R.id.main, ReturningFragment::class.java, null)
                addToBackStack("start")
            }
            parentFragmentManager.setFragmentResultListener(SingleActivity.REGISTER, this) { key, bundle ->
                debugging("Listen results")

                var txt : String? = "Welcome, "
                txt += if (bundle.getBoolean(SingleActivity.Companion.GENDER, false)) "Mr. " else "Mrs. "
                txt += bundle.getString(SingleActivity.Companion.LOGIN) + "!"
                debugging(txt)

                greeting.text = txt
            }
        }

//        if (arguments != null && requireArguments().containsKey(SingleActivity.Companion.LOGIN)){
//            var txt : String? = "Welcome, "
//            txt += if (requireArguments().getBoolean(SingleActivity.Companion.GENDER, false)) "Mr. " else "Mrs. "
//            txt += requireArguments().getString(SingleActivity.Companion.LOGIN) + "!"
//
//            val greeting : TextView = view.findViewById(R.id.tv_greeting)
//            greeting.text = txt
//        }
    }
}