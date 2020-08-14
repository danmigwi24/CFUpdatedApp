package com.dan.jamiicfapp.ui.jcahome.ui.reports

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.R

class ReportsFragment : Fragment() {

    companion object {
        fun newInstance() = ReportsFragment()
    }

    private lateinit var viewModel: ReportsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.reports_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ReportsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}