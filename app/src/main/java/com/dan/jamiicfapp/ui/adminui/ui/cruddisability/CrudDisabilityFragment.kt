package com.dan.jamiicfapp.ui.adminui.ui.cruddisability

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.databinding.FragmentCruddisabilityBinding
import com.dan.jamiicfapp.databinding.FragmentHomeBinding
import com.dan.jamiicfapp.ui.adminui.ui.cruddisability.adapter.AllDisabilitycaseAdapter
import com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel.CrudDisabilityViewModel
import com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel.CrudDisabilityViewModelFactory
import com.dan.jamiicfapp.ui.adminui.ui.donations.DonationsFragment
import com.dan.jamiicfapp.ui.jcahome.adapter.PwdDetailsAdapter
import com.dan.jamiicfapp.ui.jcahome.listerner.PwdRecyclerviewItemclicked
import com.dan.jamiicfapp.ui.jcahome.ui.home.MoreDetailsPWDSActivity
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModelFactory
import com.dan.jamiicfapp.ui.paymentmode.DonateUsingActivity
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CrudDisabilityFragment : Fragment(), KodeinAware, PwdRecyclerviewItemclicked {
    private lateinit var binding: FragmentCruddisabilityBinding
    private lateinit var crudDisabilityViewModel: CrudDisabilityViewModel
    private lateinit var allDisabilitycaseAdapter: AllDisabilitycaseAdapter
    private lateinit var sessionManager: SessionManager


    override val kodein by kodein()
    val factory by instance<CrudDisabilityViewModelFactory>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManager = SessionManager(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cruddisability, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        crudDisabilityViewModel =
            ViewModelProvider(this, factory).get(CrudDisabilityViewModel::class.java)

        Coroutines.main {
            val pwd = crudDisabilityViewModel.pwdlist.await()
            pwd.observe(requireActivity(), Observer { detailsofpwd ->
                binding.progressBarPwd.pshow()
                Log.e("DISABILITY_CASE", detailsofpwd.toString())
                try {
                    if (detailsofpwd.isNotEmpty()) {
                        binding.progressBarPwd.phide()
                        binding.recyclerView.apply {
                            allDisabilitycaseAdapter =
                                AllDisabilitycaseAdapter(this@CrudDisabilityFragment, detailsofpwd) {
                                    val intent =
                                        Intent(
                                            requireContext(),
                                            EditDisabilityActivity::class.java
                                        )
                                    intent.putExtra(INTENT_PARCELABLE_CRUD, it)
                                    startActivity(intent)
                                    sessionManager.saveDisabilityCaseId(it.disabilitycaseId)
                                }
                            layoutManager = LinearLayoutManager(activity)
                            setHasFixedSize(true)
                            adapter = allDisabilitycaseAdapter
                        }
                    } else {
                        binding.progressBarPwd.pshow()
                    }
                } catch (e: APIException) {
                    binding.progressBarPwd.phide()
                    context?.toast(e.message.toString())
                } catch (e: NoInternetException) {
                    binding.progressBarPwd.phide()
                    context?.toast(e.message.toString())
                }


            })
        }


    }

    override fun OnrecyclerviewItemClicked(view: View, disabledDetails: DisabledCaseDetails) {
        when (view.id) {
            R.id.button_edit_disability -> {
                allDisabilitycaseAdapter
                Log.e("id",sessionManager.fetchDisabilityCaseId().toString())
                //startActivity(Intent(requireContext(), EditDisabilityActivity::class.java))
            }

        }
    }

    companion object {
        const val INTENT_PARCELABLE_CRUD = "INTENT_PARCELABLE"
    }
}


