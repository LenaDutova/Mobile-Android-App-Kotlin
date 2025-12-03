package com.mobile.vedroid.kt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.databinding.FragmentStartBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.Account
import com.mobile.vedroid.kt.storage.AccountSPStore
import com.mobile.vedroid.kt.storage.SettingsDSStore
import com.mobile.vedroid.kt.ui.SingleActivity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class StartFragment : Fragment() {

    private var _binding: FragmentStartBinding? = null
    private val binding: FragmentStartBinding get() = _binding!!

    private val args: StartFragmentArgs by navArgs()
    private val sp = AccountSPStore()
    private val ds = SettingsDSStore()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugging("HI")

        binding.btnToFinal.setOnClickListener {
            debugging("Click to final")
            findNavController().navigate(R.id.action_screen_start_to_final)
        }

        binding.btnToReturning.setOnClickListener {
            debugging("Click to returning")
            findNavController().navigate(R.id.action_screen_start_to_register)
        }

        binding.fabBtnSettings.setOnClickListener {
            debugging("Click to settings")
            findNavController().navigate(R.id.action_screen_start_to_settings)
        }

        args.ACCOUNT
            ?.let { user ->
                showGreeting(user = user)
                debugging("Save new user account into SharedPreferences")
                sp.saveAccount(user)
            }
            ?: run {
                // try to read saved Account
                sp.loadAccount()
                    ?.let { user ->
                        showGreeting(user)
                        debugging("Read saved Account")
                    }
                    ?: run { debugging("No returning or saved Account") }
            }
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
}