package com.dan.jamiicfapp.ui.jcahome.ui.home

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.DisabledDetails
import com.dan.jamiicfapp.ui.paymentmode.DonateUsingActivity
import com.dan.jamiicfapp.data.db.preference.SessionManager
import kotlinx.android.synthetic.main.activity_detailspwds.*

class MoreDetailsPWDSActivity : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailspwds)

        sessionManager= SessionManager(this)



        val detailOfPWD = intent.getParcelableExtra<DisabledDetails>(HomeFragment.INTENT_PARCELABLE_DETAIL_OF_PWD)

        Glide.with(imagePWD).load(detailOfPWD.imageUrl).into(imagePWD)
        textViewNameOfPWD.text = detailOfPWD.name
        textViewHelpRequired.text=detailOfPWD.helpRequired
        textViewAmountDonated.text =
            "ksh: " + detailOfPWD.amountRequired + "of ksh: " + detailOfPWD.amountRequired + "raised"
        textViewDescription.text = detailOfPWD.disabilityDescription


        /**
         * Save user in sharedpref
         */
        sessionManager.savePhoneNumber(detailOfPWD.id.toString())

        button_donate.setOnClickListener {
            startActivity(Intent(this, DonateUsingActivity::class.java))
        }
    }
}