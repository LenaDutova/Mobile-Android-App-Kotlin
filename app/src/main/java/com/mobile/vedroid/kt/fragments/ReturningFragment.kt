package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.databinding.FragmentReturningBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account

class ReturningFragment : Fragment(/*R.layout.fragment_returning*/) {

    private var _binding: FragmentReturningBinding? = null
    private val binding: FragmentReturningBinding get() = _binding!!

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentReturningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugging("HI")

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

                val warning = buildString {
                    append(getString(R.string.text_please))
                    if (binding.registrationLogin.text.isNullOrBlank()) {
                        append(getString(R.string.text_no_name))
                    }
                    if (binding.registrationGenderToggle.checkedButtonId == R.id.btn_not_defined) {
                        append(getString(R.string.text_no_gender))
                    }
                }

                (activity as SingleActivity).showSnackBar(warning)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}