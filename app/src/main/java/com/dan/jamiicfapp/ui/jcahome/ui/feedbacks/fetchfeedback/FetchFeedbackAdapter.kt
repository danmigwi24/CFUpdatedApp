package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.fetchfeedback

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse.Feedback
import com.dan.jamiicfapp.databinding.RecyclerFetchfeedbackLayoutBinding


class FetchFeedbackAdapter(
    private val list: List<Feedback>
) :
    RecyclerView.Adapter<FetchFeedbackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_fetchfeedback_layout,
            parent,
            false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fetchfeedbackLayoutBinding.comments = list[position]
    }

    inner class ViewHolder(val fetchfeedbackLayoutBinding: RecyclerFetchfeedbackLayoutBinding) :
        RecyclerView.ViewHolder(fetchfeedbackLayoutBinding.root)


}