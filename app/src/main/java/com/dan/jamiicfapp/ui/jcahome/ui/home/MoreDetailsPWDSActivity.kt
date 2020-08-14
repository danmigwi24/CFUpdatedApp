package com.dan.jamiicfapp.ui.jcahome.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.DisabledDetails
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.ActivityDetailspwdsBinding
import com.dan.jamiicfapp.ui.commentui.CommentActivity
import com.dan.jamiicfapp.ui.paymentmode.DonateUsingActivity
import kotlinx.android.synthetic.main.activity_detailspwds.*

class MoreDetailsPWDSActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityDetailspwdsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detailspwds)
        sessionManager = SessionManager(this)


        val detailOfPWD =
            intent.getParcelableExtra<DisabledDetails>(HomeFragment.INTENT_PARCELABLE_DETAIL_OF_PWD)

        Glide.with(imagePWD).load(detailOfPWD.imageUrl).into(imagePWD)
        binding.textViewNameOfPWD.text = detailOfPWD.name
        binding.textViewHelpRequired.text = detailOfPWD.helpRequired
        binding.textViewAmountDonated.text =
            "ksh: " + detailOfPWD.amountRequired + " of ksh: " + detailOfPWD.amountRequired + " raised"
        binding.textViewDescription.text = detailOfPWD.disabilityDescription


        /**
         * Save user in sharedpref
         */
        sessionManager.savePhoneNumber(detailOfPWD.id.toString())

        binding.buttonDonate.setOnClickListener {
            startActivity(Intent(this, DonateUsingActivity::class.java))
        }
        binding.buttonSendComment.setOnClickListener {
            startActivity(Intent(this, CommentActivity::class.java))
        }
    }
}