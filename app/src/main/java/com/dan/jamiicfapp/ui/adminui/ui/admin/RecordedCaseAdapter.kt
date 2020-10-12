package com.dan.jamiicfapp.ui.adminui.ui.admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase
import com.dan.jamiicfapp.databinding.RecyclerRecordedcasesBinding


class RecordedCaseAdapter(
    private val recyclerviewItemclicked: CaseRecordRecyclerviewItemclicked,
    private val caseRecordedDetails: List<RecordedCase>,
    private val listerner: (RecordedCase) -> Unit
) :
    RecyclerView.Adapter<RecordedCaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_recordedcases,
            parent,
            false
        )
    )

    override fun getItemCount() = caseRecordedDetails.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerRecordedcasesBinding.caserecorded = caseRecordedDetails[position]

        holder.recyclerRecordedcasesBinding.btnToApprove.setOnClickListener {
            recyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.recyclerRecordedcasesBinding.btnToApprove,
                caseRecordedDetails[position]
            )
            listerner(caseRecordedDetails[position])
        }


    }

    fun deleteItem(pos: Int) {
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(val recyclerRecordedcasesBinding: RecyclerRecordedcasesBinding) :
        RecyclerView.ViewHolder(recyclerRecordedcasesBinding.root)



}