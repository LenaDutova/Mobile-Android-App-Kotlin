package com.mobile.vedroid.kt

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.material.snackbar.Snackbar
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.fragments.FinalFragment
import com.mobile.vedroid.kt.fragments.ReturningFragment
import com.mobile.vedroid.kt.fragments.StartFragment

class SingleActivity : AppCompatActivity (R.layout.activity) {

    companion object {
        const val REGISTER : String = "REGISTER"
        const val LOGIN : String = "LOGIN"
        const val GENDER : String = "GENDER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()  // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge

//        setContentView(R.layout.activity) // in constructor now
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        debugging("HI")

//        if (savedInstanceState == null) {      // or use android:name in FragmentContainerView
//            supportFragmentManager.commit {
//                setReorderingAllowed(true)
//                add<StartFragment>(R.id.main)
//            }
//        }
    }

    fun showSnackBar (message : String ){
        Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast (message : String ){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}