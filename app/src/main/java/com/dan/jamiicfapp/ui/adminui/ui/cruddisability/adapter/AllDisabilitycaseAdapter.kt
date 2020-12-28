package com.dan.jamiicfapp.ui.adminui.ui.cruddisability.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.databinding.RecyclerDisabilityAllBinding
import com.dan.jamiicfapp.ui.jcahome.listerner.PwdRecyclerviewItemclicked


class AllDisabilitycaseAdapter(
    private val recyclerviewItemclicked: PwdRecyclerviewItemclicked,
    private val detailsOfPwds: List<DisabledCaseDetails>,
    private val listerner: (DisabledCaseDetails) -> Unit
) :
    RecyclerView.Adapter<AllDisabilitycaseAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_disability_all,
            parent,
            false
        )
    )

    override fun getItemCount() = detailsOfPwds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.recyclerDisabilityAllBinding.disability = detailsOfPwds[position]

        holder.recyclerDisabilityAllBinding.buttonEditDisability.setOnClickListener {
            recyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.recyclerDisabilityAllBinding.buttonEditDisability,
                detailsOfPwds[position]
            )
            listerner(detailsOfPwds[position])

        }

    }

    fun deleteItem(pos: Int) {
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(val recyclerDisabilityAllBinding: RecyclerDisabilityAllBinding) :
        RecyclerView.ViewHolder(recyclerDisabilityAllBinding.root)


}