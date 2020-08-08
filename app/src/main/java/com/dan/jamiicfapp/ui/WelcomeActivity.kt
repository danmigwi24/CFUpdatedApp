package com.dan.jamiicfapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.ui.auth.LoginActivity
import com.dan.jamiicfapp.ui.auth.RegisterActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_welcome)

        button_Login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
        button_Register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}