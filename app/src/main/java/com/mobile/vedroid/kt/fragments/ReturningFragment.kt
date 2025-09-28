package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.extensions.debugging

class ReturningFragment : Fragment(R.layout.fragment_returning) {

    lateinit var login : TextInputEditText
    lateinit var toggle : MaterialButtonToggleGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.debugging("HI")

//        activity?.onBackPressedDispatcher.addCallback() {
//            findNavController().navigate(R.id.action_screen_register_return_start)
//        }

        login = view.findViewById(R.id.login)
        toggle = view.findViewById(R.id.toggle_button)

        val btnStart : Button = view.findViewById(R.id.btn_to_start)
        btnStart.setOnClickListener {
            debugging("Click to final")

            if (!login.text.isNullOrBlank() && toggle.checkedButtonId != R.id.btn_not_defined) {
                val args = Bundle()
                args.putString(SingleActivity.LOGIN, login.text.toString())
                args.putBoolean(SingleActivity.GENDER, (toggle.checkedButtonId == R.id.btn_man))

                findNavController().navigate(R.id.action_screen_register_return_start, args)
                return@setOnClickListener
            } else {
                if (login.text.isNullOrBlank()) (activity as SingleActivity).showSnackBar("Enter name")
                if (toggle.checkedButtonId == R.id.btn_not_defined) (activity as SingleActivity).showToast("Choose gender")
            }
        }
    }
}