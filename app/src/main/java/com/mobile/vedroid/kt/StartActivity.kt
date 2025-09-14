package com.mobile.vedroid.kt

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobile.vedroid.kt.extensions.debugging

class StartActivity : AppCompatActivity() {

    companion object {
        const val REQUEST : Int = 100
        const val LOGIN : String = "LOGIN"
        const val GENDER : String = "GENDER"
    }

    lateinit var resultLauncher : ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        enableEdgeToEdge()  // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        debugging("HI")

        val btnFinal : Button = findViewById(R.id.btn_to_final)
        btnFinal.setOnClickListener {
            debugging("Click to final")
            openActivity()
        }

        val btnReturning : Button = findViewById(R.id.btn_to_returning)
        btnReturning.setOnClickListener {
            debugging("Click to returning")
            openActivityForResult()
        }

        // define before onStart()
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                useResult(data)
            }
        }
    }

    private fun openActivity(): Unit {
        val intent = Intent(this, FinalActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openActivityForResult(): Unit {
        val intent = Intent(this, ReturningActivity::class.java)

//        startActivityForResult(intent, REQUEST)   // @Deprecated way
        resultLauncher.launch(intent)   // New way
    }

//    // @Deprecated
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        if (requestCode == REQUEST && resultCode == RESULT_OK){
//            useResult(data)
//        } else {
//            logToDebug("onActivityResult: request = $REQUEST, result = $resultCode" )
//        }
//        super.onActivityResult(requestCode, resultCode, data)
//    }

    private fun useResult(data: Intent?) : Unit {
        if (data != null && data.hasExtra(LOGIN)){
            var txt : String? = "Welcome, "
            txt += if (data.getBooleanExtra(GENDER, false)) "Mr." else "Mrs."
            txt += data.getStringExtra(LOGIN) + "!"

            val greeting : TextView = findViewById(R.id.tv_greeting)
            greeting.text = txt
        }
    }
}