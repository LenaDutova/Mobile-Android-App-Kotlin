package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.databinding.FragmentStartBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account

class StartFragment : Fragment(R.layout.fragment_start) {

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding
        get() = _binding ?: throw RuntimeException()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        _binding = FragmentStartBinding.bind(view)

//        binding.btnToFinal.setOnClickListener (Navigation.createNavigateOnClickListener(R.id.action_screen_start_to_final))
        binding.btnToFinal.setOnClickListener {
            debugging("Click to final")
            findNavController().navigate(R.id.action_screen_start_to_final)
        }

//        binding.btnToReturning.setOnClickListener ( Navigation.createNavigateOnClickListener(R.id.action_screen_start_to_register))
        binding.btnToReturning.setOnClickListener {
            debugging("Click to returning")
            findNavController().navigate(R.id.action_screen_start_to_register)
        }

        val args: StartFragmentArgs by navArgs()
        if (args.ACCOUNT != null) showGreeting(args.ACCOUNT!!)
        // if (!args.LOGIN.isNullOrBlank()) showGreeting(args.GENDER, args.LOGIN!!)
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

    private fun showGreeting(gender : Boolean, login: String){
        var txt : String? = getString(R.string.text_greeting) + " "
        txt += if (gender) getString(R.string.text_mr) else getString(R.string.text_mrs)
        txt += " $login!"
        debugging("Greeting: $txt")

        binding.greetingText.text = txt
    }
}