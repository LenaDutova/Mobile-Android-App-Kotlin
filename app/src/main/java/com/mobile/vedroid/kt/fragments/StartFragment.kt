package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.databinding.FragmentStartBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() = _binding ?: throw RuntimeException()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        binding.btnToFinal.setOnClickListener {
            debugging("Click to final")
            findNavController().navigate(R.id.action_screen_start_to_final)
        }

        binding.btnToReturning.setOnClickListener {
            debugging("Click to returning")
            findNavController().navigate(R.id.action_screen_start_to_register)
        }

        val args: StartFragmentArgs by navArgs()
        if (args.ACCOUNT != null) showGreeting(args.ACCOUNT!!)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showGreeting(user : Account) {
        var txt : String? = getString(R.string.text_greeting) + " "
        txt += if (user.gender) getString(R.string.text_mr) else getString(R.string.text_mrs)
        txt += " " + user.login + "!"
        debugging("Greeting: $txt")

        binding.greetingText.text = txt
    }
}