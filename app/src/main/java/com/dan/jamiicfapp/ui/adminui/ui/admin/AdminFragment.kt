package com.dan.jamiicfapp.ui.adminui.ui.admin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase
import com.dan.jamiicfapp.databinding.FragmentHomeadminBinding
import com.dan.jamiicfapp.ui.adminui.AdminActivity
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModelFactory
import com.dan.jamiicfapp.utils.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class AdminFragment : Fragment(), KodeinAware, CaseRecordRecyclerviewItemclicked {

    override val kodein by kodein()
    private val factory by instance<RecordcaseViewModelFactory>()
    private lateinit var binding: FragmentHomeadminBinding

    private lateinit var viewModel: RecordcaseViewModel
    private lateinit var sessionManager: SessionManager
    private lateinit var recordedCaseAdapter: RecordedCaseAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sessionManager = SessionManager(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homeadmin, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, factory).get(RecordcaseViewModel::class.java)

        Coroutines.main {
            val recordcase = viewModel.recordedcasesVM.await()
            recordcase.observe(requireActivity(), Observer { listOfRecordedCase ->
                binding.progressBarRecordedcases.pshow()
                try {
                    if (listOfRecordedCase.isNotEmpty()) {
                        binding.progressBarRecordedcases.phide()
                        binding.reclyerviewRecords.apply {
                            recordedCaseAdapter =
                                RecordedCaseAdapter(this@AdminFragment, listOfRecordedCase) {
                                    val intent =
                                        Intent(
                                            requireContext(),
                                            MoreAboutARecordActivity::class.java
                                        )
                                    intent.putExtra(INTENT_PARCELABLE_RECORD_DETAILS, it)
                                    startActivity(intent)
                                    sessionManager.saveRecordedCaseId(it.id)
                                }
                            layoutManager = LinearLayoutManager(activity)
                            setHasFixedSize(true)
                            adapter = recordedCaseAdapter
                        }
                    } else {
                        binding.progressBarRecordedcases.pshow()
                    }
                } catch (e: APIException) {
                    binding.progressBarRecordedcases.phide()
                    context?.toast(e.message.toString())
                } catch (e: NoInternetException) {
                    binding.progressBarRecordedcases.phide()
                    context?.toast(e.message.toString())
                }
            })
        }
    }

    override fun OnrecyclerviewItemClicked(view: View, caseRecorded: RecordedCase) {

        when (view.id) {
            R.id.btnToApprove -> {
                recordedCaseAdapter
                /*lifecycleScope.launch {
                    try {
                        Log.e("caseRecorded", sessionManager.fetchRecordedCaseId().toString())
                        if (caseRecorded.id != 0) {
                            val response = viewModel.updateRecordCase(
                                caseRecorded.id, "approved"
                            )
                            if (response.isSuccessful) {
                                toast(response.message.toString())
                                binding.progressBarRecordedcases.visibility = View.GONE
                                startActivity(
                                    Intent(
                                        requireContext(),
                                        AdminActivity::class.java
                                    )
                                )
                                requireActivity().finish()
                                Log.e("caseRecorded", response.caseRecorded.toString())
                            } else {
                                toast("Please try again later ")
                                binding.progressBarRecordedcases.visibility = View.GONE
                            }
                        } else {
                            toast("Please to reload this page again ")
                            binding.progressBarRecordedcases.visibility = View.GONE
                        }


                    } catch (e: APIException) {
                        toast(e.message.toString())
                        Log.e("APIException", e.toString())
                        binding.progressBarRecordedcases.visibility = View.GONE

                    } catch (e: NoInternetException) {
                        toast(e.message.toString())
                        Log.e("NoInternetException", e.toString())
                        binding.progressBarRecordedcases.visibility = View.GONE

                    }
                }*/


            }

        }
    }

    companion object {
        const val INTENT_PARCELABLE_RECORD_DETAILS = "INTENT_PARCELABLE_RECORD_DETAILS+"
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}