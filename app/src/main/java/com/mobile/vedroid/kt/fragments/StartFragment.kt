package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.extensions.debugging

class StartFragment : Fragment(R.layout.fragment_start) {

    lateinit var greeting : TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugging("HI")

        greeting = view.findViewById(R.id.tv_greeting)

        val btnFinal : Button = view.findViewById(R.id.btn_to_final)
        btnFinal.setOnClickListener {
            debugging("Click to final")
            parentFragmentManager.commit {
                replace<FinalFragment>(R.id.main)    // Kill this fragment and open new
            }
        }

        val btnReturning : Button = view.findViewById(R.id.btn_to_returning)
        btnReturning.setOnClickListener {
            debugging("Click to returning")
            parentFragmentManager.commit {
                add<ReturningFragment>(R.id.main)   // Show new fragment on top of this (need background)
                addToBackStack("start")
            }
            parentFragmentManager.setFragmentResultListener(SingleActivity.REGISTER, this) { key, bundle ->
                debugging("Listen results")
                showGreeting(bundle);
            }
        }
    }

    private fun showGreeting(bundle: Bundle){
        val txt = buildString {
            append(getString(R.string.text_greeting))
            append(' ')
            append(if (bundle.getBoolean(SingleActivity.Companion.GENDER, false)) getString(R.string.text_mr) else getString(R.string.text_mrs))
            append(' ')
            append(bundle.getString(SingleActivity.Companion.LOGIN))
            append('!')
        }

        debugging("Greeting: $txt")
        greeting.text = txt
    }
}