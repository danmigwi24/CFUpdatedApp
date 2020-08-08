package com.dan.jamiicfapp.ui.paymentmode

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.databinding.ActivityDonateUsingBinding
import kotlinx.android.synthetic.main.activity_donate_using.*

class DonateUsingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDonateUsingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_donate_using)

        setSupportActionBar(toolbar2)

        binding.cardMpesaPaymentMethod.setOnClickListener {
            startActivity(Intent(this, Mpesa2Activity::class.java))
        }
    }
}