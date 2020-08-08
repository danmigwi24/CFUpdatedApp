package com.dan.jamiicfapp.ui.chief.newpwds

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.databinding.ActivityAddPwdBinding
import kotlinx.android.synthetic.main.activity_add_pwd.*
import java.util.*


class AddPwdActivity : AppCompatActivity() {
    var day: Int = 0
    var month: Int = 0
    var year: Int = 0

    lateinit var mCalendar: Calendar

    private lateinit var binding: ActivityAddPwdBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_pwd)

        mCalendar = Calendar.getInstance()
        day = mCalendar.get(Calendar.DAY_OF_MONTH)
        year = mCalendar.get(Calendar.YEAR)
        month = mCalendar.get(Calendar.MONTH)

        dateOfBirth()
        changeVisibility()



        button_Continue.setOnClickListener {

        }
    }

    private fun changeVisibility() {
        button_step2.setOnClickListener {
            if (appCompatTextView.text == "Add New Disabled Person") {
                constraintLayout.visibility = View.GONE
                constraintLayout2.visibility = View.VISIBLE
                appCompatTextView.text = "Relationship Status"
                button_Continue.visibility = View.GONE
            }
        }

        button_backToStep1.setOnClickListener {
            if (appCompatTextView.text == "Relationship Status") {
                constraintLayout2.visibility = View.GONE
                constraintLayout.visibility = View.VISIBLE
                appCompatTextView.text = "Add New Disabled Person"
                button_Continue.visibility = View.GONE
            }
        }
        button_step3.setOnClickListener {
            if (appCompatTextView.text == "Relationship Status") {
                constraintLayout2.visibility = View.GONE
                constraintLayout3.visibility = View.VISIBLE
                appCompatTextView.text = "Adress Status"
                button_Continue.visibility = View.VISIBLE
            }
        }
        button_backToStep2.setOnClickListener {
            if (appCompatTextView.text == "Adress Status") {
                constraintLayout3.visibility = View.GONE
                constraintLayout2.visibility = View.VISIBLE
                appCompatTextView.text = "Relationship Status"
                button_Continue.visibility = View.GONE
            }
        }
    }

    private fun dateOfBirth() {
        selectDOB.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    selectDOB.setText("" + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year)
                },
                year,
                month,
                day
            )

            datePickerDialog.show()
        }
    }
}