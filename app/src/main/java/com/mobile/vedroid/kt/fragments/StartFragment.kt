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

class StartFragment : Fragment(/*R.layout.fragment_start*/) {

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding get() = _binding!!

    private val args: StartFragmentArgs by navArgs()

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated (view: View, savedInstanceState: Bundle?) {
        super.onViewCreated (view, savedInstanceState)
        debugging("HI")

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

        args.ACCOUNT?.let { user -> showGreeting(user = user) }
        // if (args.ACCOUNT != null) showGreeting(args.ACCOUNT!!)
        // if (!args.LOGIN.isNullOrBlank()) showGreeting(args.GENDER, args.LOGIN!!)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun showGreeting(user : Account) {
        val txt = buildString {
            append(getString(R.string.text_greeting))
            append(' ')
            append(getString(if (user.gender) R.string.text_mr else R.string.text_mrs))
            append(' ')
            append(user.login)
            append('!')
        }

        debugging("Greeting: $txt")
        binding.greetingText.text = txt
    }

    private fun showGreeting(gender : Boolean, login: String){
        val txt = buildString {
            append(getString(R.string.text_greeting))
            append(' ')
            append(getString(if (gender) R.string.text_mr else R.string.text_mrs))
            append(' ')
            append(login)
            append('!')
        }

        debugging("Greeting: $txt")
        binding.greetingText.text = txt
    }
}