package com.dan.jamiicfapp.ui.jcahome.ui.home

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
import com.dan.jamiicfapp.databinding.FragmentHomeBinding
import com.dan.jamiicfapp.ui.jcahome.adapter.PwdDetailsAdapter
import com.dan.jamiicfapp.ui.jcahome.listerner.PwdRecyclerviewItemclicked
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModel
import com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel.HomeViewModelFactory
import com.dan.jamiicfapp.ui.paymentmode.DonateUsingActivity
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment : Fragment(), KodeinAware, PwdRecyclerviewItemclicked {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var pwdDetailsAdapter: PwdDetailsAdapter
    private lateinit var sessionManager: SessionManager


    override val kodein by kodein()
    val factory by instance<HomeViewModelFactory>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sessionManager = SessionManager(requireContext())
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeViewModel =
            ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        Coroutines.main {
            val pwd = homeViewModel.pwdlist.await()
            pwd.observe(requireActivity(), Observer { detailsofpwd ->
                binding.progressBarPwd.pshow()
                Log.e("Detailsofpwd", detailsofpwd.toString())
                try {
                    if (detailsofpwd.isNotEmpty()) {
                        binding.progressBarPwd.phide()
                        binding.recyclerView.apply {
                            pwdDetailsAdapter = PwdDetailsAdapter(this@HomeFragment, detailsofpwd) {
                                val intent =
                                    Intent(requireContext(), MoreDetailsPWDSActivity::class.java)
                                intent.putExtra(INTENT_PARCELABLE_DETAIL_OF_PWD, it)
                                startActivity(intent)
                            }
                            layoutManager = LinearLayoutManager(activity)
                            setHasFixedSize(true)
                            adapter = pwdDetailsAdapter
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
            R.id.button_donate -> {
                sessionManager.savePwdId(disabledDetails.disabilitycaseId.toString())
                startActivity(Intent(requireContext(), DonateUsingActivity::class.java))

            }
            R.id.textViewNameOfPWD -> {
                pwdDetailsAdapter
            }
            R.id.textViewReadMore -> {
                pwdDetailsAdapter
            }
            R.id.imagePWD->{
                pwdDetailsAdapter
            }
        }
    }

    companion object {
        const val INTENT_PARCELABLE_DETAIL_OF_PWD = "INTENT_PARCELABLE"
    }


}