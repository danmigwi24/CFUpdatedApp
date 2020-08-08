package com.dan.jamiicfapp.ui.jcahome.ui.events

import android.app.ProgressDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.dan.jamiicfapp.databinding.ActivityMoreabouteventsBinding

class MoreAboutEventsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMoreabouteventsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_moreaboutevents)


        val event = intent.getParcelableExtra<JcaEvent>(EventsFragment.INTENT_PARCABLE_EVENTS)

        Glide.with(binding.imageEvents).load(event.eventImageurl).into(binding.imageEvents)
        binding.textViewTitleEvent.text = event.eventTitle
        binding.textViewDateTime.text =
            "Date: ${event.eventDate} \n Start At: ${event.eventStartTime} \n Ends At:${event.eventEndingTime}"
        binding.textViewDescriptionEvent.text = event.eventDescription
        binding.textViewVenue.text = event.eventVenue

        binding.buttonAttend.setOnClickListener {
            val progressDialog = ProgressDialog(this)
            progressDialog.setTitle("Welcome Member")
            progressDialog.setMessage("Thanks for using our application")
            progressDialog.setCancelable(false)
            progressDialog.setButton(
                DialogInterface.BUTTON_NEGATIVE,
                "Cancel"
            ) { _: DialogInterface, _: Int ->
                progressDialog.dismiss()
            }
            progressDialog.show()
        }


    }
}