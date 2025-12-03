package com.mobile.vedroid.kt.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar
import com.mobile.vedroid.kt.databinding.ActivityBinding
import com.mobile.vedroid.kt.extensions.debugging
import java.util.Locale

class SingleActivity : AppCompatActivity() {

    private var _binding: ActivityBinding? = null
    private val binding: ActivityBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge

        _binding = ActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        ViewCompat.setOnApplyWindowInsetsListener(view) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }


    fun showSnackBar (message : String ){
        Snackbar.make(binding.navHostFragment, message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast (message : String ){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}