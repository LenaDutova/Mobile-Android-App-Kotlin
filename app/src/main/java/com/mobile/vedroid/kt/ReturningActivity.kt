package com.mobile.vedroid.kt

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButtonToggleGroup
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mobile.vedroid.kt.extensions.debugging

class ReturningActivity : AppCompatActivity() {

    lateinit var login : TextInputEditText
    lateinit var toggle : MaterialButtonToggleGroup

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_returning)

        enableEdgeToEdge()  // reed https://developer.android.com/develop/ui/views/layout/edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        debugging("HI")


        login = findViewById(R.id.login)
        toggle = findViewById(R.id.toggle_button)

        val btnStart : Button = findViewById(R.id.btn_to_start)
        btnStart.setOnClickListener {
            debugging("Click to final")
            returnToActivity()
        }
    }

    private fun returnToActivity(){
        val intent = Intent()

        if (!login.text.isNullOrBlank()) {
            intent.putExtra(StartActivity.LOGIN, login.text.toString())
        } else {
            Snackbar.make(findViewById(R.id.main), "Enter name", Snackbar.LENGTH_LONG).show()
            return
        }

        if (toggle.checkedButtonId != R.id.btn_not_defined) {
            intent.putExtra(StartActivity.GENDER, (toggle.checkedButtonId == R.id.btn_man))
        } else {
            Snackbar.make(findViewById(R.id.main), "Choose gender", Snackbar.LENGTH_LONG).show()
            return
        }

        setResult(RESULT_OK, intent)
        finish()
    }
}