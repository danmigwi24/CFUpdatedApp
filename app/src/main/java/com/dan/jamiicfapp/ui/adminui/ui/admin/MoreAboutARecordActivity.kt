package com.dan.jamiicfapp.ui.adminui.ui.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase
import com.dan.jamiicfapp.databinding.ActivityMoreaboutarecordBinding
import com.dan.jamiicfapp.ui.adminui.AdminActivity
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModelFactory
import com.dan.jamiicfapp.utils.APIException
import com.dan.jamiicfapp.utils.NoInternetException
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MoreAboutARecordActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<RecordcaseViewModelFactory>()
    private lateinit var bindingImpl: ActivityMoreaboutarecordBinding

    private lateinit var viewModel: RecordcaseViewModel
    private lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingImpl = DataBindingUtil.setContentView(this, R.layout.activity_moreaboutarecord)

        viewModel =
            ViewModelProvider(this, factory).get(RecordcaseViewModel::class.java)

        sessionManager = SessionManager(this)

        val detail =
            intent.getParcelableExtra<RecordedCase>(AdminFragment.INTENT_PARCELABLE_RECORD_DETAILS)

        bindingImpl.textViewdisabilityCase.text = "CHALLENGES FACED BY DISABLED PERSON: \n ${detail.disabilityCase} "
        bindingImpl.textViewDescription.text = "DESCRIPTION: ${detail.description}"
        bindingImpl.textViewdisabledPersonName.text =
            "DISABLED PERSON NAME: ${detail.disabledPersonName}"
        bindingImpl.textViewLocation.text = "LOCATION: ${detail.location}"
        bindingImpl.textViewisApproved.text = "APPROVAL STATUS ${detail.isApproved}"
        bindingImpl.textViewtypeOfDisability.text =
            "TYPE OF DISABILITY : ${detail.typeOfDisability}"

        bindingImpl.btnApprove.setOnClickListener {
            bindingImpl.progressBarMoreRecordDetails.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    Log.e("caseRecorded", sessionManager.fetchRecordedCaseId().toString())
                    if (sessionManager.fetchRecordedCaseId()!!.toInt() != 0) {
                        val response = viewModel.updateRecordCase(
                            sessionManager.fetchRecordedCaseId()!!.toInt(), "approved"
                        )
                        if (response.isSuccessful) {
                            toast(response.message.toString())
                            bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE
                            startActivity(
                                Intent(
                                    this@MoreAboutARecordActivity,
                                    AdminActivity::class.java
                                )
                            )
                            this@MoreAboutARecordActivity.finish()
                            Log.e("caseRecorded", response.caseRecorded.toString())
                        } else {
                            toast("Please try again later ")
                            bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE
                        }
                    } else {
                        toast("Please to reload this page again ")
                        bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE
                    }


                } catch (e: APIException) {
                    toast(e.message.toString())
                    Log.e("APIException", e.toString())
                    bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE

                } catch (e: NoInternetException) {
                    toast(e.message.toString())
                    Log.e("NoInternetException", e.toString())
                    bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE

                }
            }
        }
        bindingImpl.btnDisapprove.setOnClickListener {
            bindingImpl.progressBarMoreRecordDetails.visibility = View.VISIBLE
            lifecycleScope.launch {
                try {
                    if (sessionManager.fetchRecordedCaseId()!!.toInt() != 0) {
                        val response = viewModel.updateRecordCase(
                            sessionManager.fetchRecordedCaseId()!!.toInt(), "Disapproved"
                        )
                        if (response.isSuccessful) {
                            toast(response.message.toString())
                            bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE
                            startActivity(
                                Intent(
                                    this@MoreAboutARecordActivity,
                                    AdminActivity::class.java
                                )
                            )
                            this@MoreAboutARecordActivity.finish()
                            Log.e("caseRecorded", response.caseRecorded.toString())
                        } else {
                            toast("Please try again later ")
                            bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE
                        }
                    } else {
                        toast("Please to reload this page again ")
                        bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE
                    }


                } catch (e: APIException) {
                    toast(e.message.toString())
                    Log.e("APIException", e.toString())
                    bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE

                } catch (e: NoInternetException) {
                    toast(e.message.toString())
                    Log.e("NoInternetException", e.toString())
                    bindingImpl.progressBarMoreRecordDetails.visibility = View.GONE

                }
            }
        }
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}



