package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.databinding.FragmentReturningBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account

class ReturningFragment : Fragment(R.layout.fragment_returning) {

    private var _binding: FragmentReturningBinding? = null
    private val binding: FragmentReturningBinding
        get() = _binding ?: throw RuntimeException()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        _binding = FragmentReturningBinding.bind(view)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            debugging("Back stack click")
            findNavController().popBackStack()
        }

        val btnStart : Button = binding.btnToStart
        btnStart.setOnClickListener {
            if (!binding.registrationLogin.text.isNullOrBlank()
                && binding.registrationGenderToggle.checkedButtonId != R.id.btn_not_defined) {
                debugging("Click from returning with registration data")

                val action = ReturningFragmentDirections.actionScreenRegisterReturnStart(
                    ACCOUNT = Account(
                        binding.registrationLogin.text.toString(),
                        binding.registrationGenderToggle.checkedButtonId == R.id.btn_man),
                    LOGIN = binding.registrationLogin.text.toString(),
                    GENDER = (binding.registrationGenderToggle.checkedButtonId == R.id.btn_man))

                findNavController().navigate(action)
            } else {
                debugging("Click from returning, but without some params")

                var warning: String = getString(R.string.text_please)
                if (binding.registrationLogin.text.isNullOrBlank()) warning += getString(R.string.text_no_name)
                if (binding.registrationGenderToggle.checkedButtonId == R.id.btn_not_defined)
                    warning += getString(R.string.text_no_gender)

                (activity as SingleActivity).showSnackBar(warning)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}