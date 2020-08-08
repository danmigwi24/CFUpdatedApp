package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dan.jamiicfapp.R


class FeedbacksFragment : Fragment() {

    private lateinit var dashboardViewModel: FeedbacksViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(FeedbacksViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_feedbacks, container, false)
        val textView: TextView = root.findViewById(R.id.text_feedbacks)
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}