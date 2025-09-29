package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.textfield.TextInputEditText
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account

class ReturningFragment : Fragment(R.layout.fragment_returning) {

    lateinit var login : TextInputEditText
    lateinit var toggle : MaterialButtonToggleGroup

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        login = view.findViewById(R.id.login)
        toggle = view.findViewById(R.id.toggle_button)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            debugging("Back stack click")
            findNavController().navigateUp()
        }

        val btnStart : Button = view.findViewById(R.id.btn_to_start)
        btnStart.setOnClickListener {
            if (!login.text.isNullOrBlank() && toggle.checkedButtonId != R.id.btn_not_defined) {
                debugging("Click from returning with registration data")

                val action = ReturningFragmentDirections.actionScreenRegisterReturnStart(
                    ACCOUNT = Account(login.text.toString(), (toggle.checkedButtonId == R.id.btn_man)),
                    LOGIN = login.text.toString(),
                    GENDER = (toggle.checkedButtonId == R.id.btn_man))
                findNavController().navigate(action)
            } else {
                debugging("Click from returning, but without some params")

                if (login.text.isNullOrBlank()) (activity as SingleActivity).showSnackBar("Enter name")
                if (toggle.checkedButtonId == R.id.btn_not_defined) (activity as SingleActivity).showToast("Choose gender")
            }
        }
    }
}