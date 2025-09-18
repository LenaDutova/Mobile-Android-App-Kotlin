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
        const val JUMP_TO_RETURNING = 1
        const val JUMP_FROM_RETURNING = 2
        const val JUMP_TO_FINAL = 3

        const val LOGIN : String = "LOGIN"
        const val GENDER : String = "GENDER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)

        enableEdgeToEdge()  // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        debugging("HI")

        // or use android:name in FragmentContainerView
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<StartFragment>(R.id.main)
            }
        }
    }

    fun navigate (jump : Int, args: Bundle? = null) : Unit {
        when (jump) {
            JUMP_TO_RETURNING -> {
                debugging("Open fragment to registration")

                supportFragmentManager.commit {
                    replace(R.id.main, ReturningFragment::class.java, null)
                    setReorderingAllowed(true)
                    addToBackStack("start")
                }
            }
            JUMP_FROM_RETURNING -> {
                debugging("Return to start fragment with registration data")

                if (args != null) supportFragmentManager.popBackStack()
                supportFragmentManager.commit {
                    replace(R.id.main, StartFragment::class.java, args)
                    setReorderingAllowed(true)
                }
            }
            JUMP_TO_FINAL -> {
                debugging("Open fragment with recycle view list")

                supportFragmentManager.commit {
                    replace(R.id.main, FinalFragment::class.java, null)
                    setReorderingAllowed(true)
                }
            }
            else ->{debugging("Navigate Error")}
        }
    }

    fun showSnackBar (message : String ){
        Snackbar.make(findViewById(R.id.main), message, Snackbar.LENGTH_LONG).show()
    }

    fun showToast (message : String ){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}