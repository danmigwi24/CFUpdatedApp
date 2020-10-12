package com.dan.jamiicfapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.ui.auth.LoginActivity
import com.dan.jamiicfapp.ui.auth.RegisterActivity
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )*/
        setContentView(R.layout.activity_welcome)


        textmarquee.isSelected = true
        button_Login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        button_Register.setOnClickListener {
            btnRegister()
        }
        button_Register2.setOnClickListener {
            btnRegister()
        }
    }

    private fun btnRegister() {
        startActivity(Intent(this, RegisterActivity::class.java))

    }
}