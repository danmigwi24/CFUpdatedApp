package com.dan.jamiicfapp.ui.adminui.ui.donations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.R
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class DonationsFragment : Fragment(), KodeinAware {
    override val kodein by kodein()
    private val factory by instance<DonationListViewModelFactory>()
    private lateinit var viewModel: DonationsViewModel
    //private lateinit val bind

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this, factory).get(DonationsViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_donationlist, container, false)

        return root
    }


}