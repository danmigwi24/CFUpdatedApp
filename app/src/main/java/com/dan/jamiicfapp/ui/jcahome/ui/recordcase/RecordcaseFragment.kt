package com.dan.jamiicfapp.ui.jcahome.ui.recordcase

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.FragmentRecordcaseBinding
import com.dan.jamiicfapp.ui.jcahome.HomeActivity
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel.RecordcaseViewModelFactory
import com.dan.jamiicfapp.utils.APIException
import com.dan.jamiicfapp.utils.Coroutines
import com.dan.jamiicfapp.utils.NoInternetException
import com.dan.jamiicfapp.utils.toast
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class RecordcaseFragment : Fragment(), KodeinAware {

    override val kodein by kodein()
    val factory by instance<RecordcaseViewModelFactory>()

    private lateinit var recordcaseViewModel: RecordcaseViewModel
    private lateinit var binding: FragmentRecordcaseBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        recordcaseViewModel =
            ViewModelProvider(this, factory).get(RecordcaseViewModel::class.java)
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recordcase,
            container,
            false
        )

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Coroutines.main {
            sessionManager = SessionManager(requireActivity())
            Log.e("dan", sessionManager.fetchUserId().toString())
            binding.btnRecordCase.setOnClickListener {

                val memberCommunityId = sessionManager.fetchUserId().toString()
                val disabilityCase = binding.editTextCaseName.text.toString()
                val description = binding.editTextDescription.text.toString()
                val location = binding.editTextLocation.text.toString()
                val disabledPersonName = binding.editTextDisabledName.text.toString()
                val typeOfDisability = binding.editTextTypeOfDisability.text.toString()

                when {
                    disabilityCase.isEmpty() -> {
                        toast("Please Enter Name")

                    }
                    location.isEmpty() -> {
                        toast("Please Enter location")
                    }
                    disabledPersonName.isEmpty() -> {
                        toast("Please Enter disabledPersonName")
                    }
                    typeOfDisability.isEmpty() -> {
                        toast("Please Enter typeOfDisability")
                    }
                    else -> {
                        binding.progressBar4.visibility = View.VISIBLE
                        lifecycleScope.launch {
                            try {
                                val response = recordcaseViewModel.recordcaseVM(
                                    memberCommunityId,
                                    disabilityCase,
                                    description,
                                    location,
                                    disabledPersonName,
                                    typeOfDisability,
                                    ""
                                )
                                if (response.isSuccessful) {
                                    toast(response.message.toString())
                                    binding.progressBar4.visibility = View.GONE
//                                    startActivity(
//                                        Intent(
//                                            activity,
//                                            HomeActivity::class.java
//                                        )
//                                    )
                                    binding.editTextCaseName.setText("")
                                    binding.editTextDescription.setText("")
                                    binding.editTextLocation.setText("")
                                    binding.editTextDisabledName.setText("")
                                    binding.editTextTypeOfDisability.setText("")
                                } else {
                                    toast("Please check your details ")
                                    binding.progressBar4.visibility = View.GONE
                                }

                            } catch (e: APIException) {
                                toast(e.message.toString())
                                Log.e("APIException", e.toString())
                                binding.progressBar4.visibility = View.GONE

                            } catch (e: NoInternetException) {
                                toast(e.message.toString())
                                Log.e("NoInternetException", e.toString())
                                binding.progressBar4.visibility = View.GONE

                            }
                        }
                    }
                }
            }
        }

    }

    private fun toast(s: String) {
        Toast.makeText(requireContext(), s, Toast.LENGTH_SHORT).show();
    }

    private fun alertDialog() {
        val alertDialog = AlertDialog.Builder(requireContext())
        alertDialog.setTitle("Thanks for Donating to JCF")
        alertDialog.setMessage("Thanks for making this process successful We are proud of you")
        alertDialog.setPositiveButton(android.R.string.yes) { dialog, which ->
            context?.toast("Thanks for supporting JCF")
            startActivity(Intent(requireContext(), HomeActivity::class.java))
            requireActivity().finish()
        }

    }
}