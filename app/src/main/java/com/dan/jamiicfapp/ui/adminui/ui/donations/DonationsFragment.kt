package com.dan.jamiicfapp.ui.adminui.ui.donations

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
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation.DonationList
import com.dan.jamiicfapp.databinding.FragmentDonationlistBinding
import com.dan.jamiicfapp.utils.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DonationsFragment : Fragment(), KodeinAware, DonationRecyclerviewItemclicked {
    override val kodein by kodein()
    private val factory by instance<DonationListViewModelFactory>()
    private lateinit var viewModel: DonationsViewModel
    private lateinit var binding: FragmentDonationlistBinding
    private lateinit var donationListAdapter: DonationListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_donationlist, container, false)
        viewModel =
            ViewModelProvider(this, factory).get(DonationsViewModel::class.java)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Coroutines.main {
            val pwd = viewModel.donationlist.await()
            pwd.observe(requireActivity(), Observer { donation ->
                binding.progressBar5.pshow()
                Log.e("Detailsofpwd", donation.toString())
                try {
                    if (donation.isNotEmpty()) {
                        binding.progressBar5.phide()
                       // binding.textGallery.text = donation.size.toString()
                        binding.reclyerviewDonationlist.apply {
                            donationListAdapter =
                                DonationListAdapter(this@DonationsFragment, donation) {
//                                val intent =
//                                    Intent(requireContext(), MoreDetailsPWDSActivity::class.java)
//                                intent.putExtra(HomeFragment.INTENT_PARCELABLE_DETAIL_OF_PWD, it)
//                                startActivity(intent)
                                    context?.toast("Donations list")
                                }
                            layoutManager = LinearLayoutManager(activity)
                            setHasFixedSize(true)
                            adapter = donationListAdapter
                        }
                    } else {
                        binding.progressBar5.pshow()
                    }
                } catch (e: APIException) {
                    binding.progressBar5.phide()
                    context?.toast(e.message.toString())
                } catch (e: NoInternetException) {
                    binding.progressBar5.phide()
                    context?.toast(e.message.toString())
                }


            })
        }
    }

    override fun OnrecyclerviewItemClicked(view: View, donationList: DonationList) {
        when (view.id) {
            R.id.button_donateList -> {
                donationListAdapter
            }
        }
    }


}