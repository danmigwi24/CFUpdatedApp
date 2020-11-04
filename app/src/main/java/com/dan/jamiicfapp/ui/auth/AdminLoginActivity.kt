package com.dan.jamiicfapp.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.databinding.ActivityAdminLoginBinding
import com.dan.jamiicfapp.ui.adminui.AdminActivity

class AdminLoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAdminLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_admin_login)

        binding.buttonLoginadmin.setOnClickListener {
            val phonenumber = binding.editTextPhonenumber.text.toString()
            if (phonenumber.isEmpty()) {
                binding.editTextPhonenumber.error = "Please enter valid number"
            }
            val length = binding.editTextPin.text.length
            if (binding.editTextPin.text.toString().isEmpty() && length != 4) {
                binding.editTextPin.error = "Please enter valid number"
            }
            if (binding.editTextPin.text.toString() == "1234" && binding.editTextPhonenumber.text.toString() == "123456789"
            ) {
                startActivity(Intent(this, AdminActivity::class.java))
            }
        }


    }
}