package com.dan.jamiicfapp.ui.jcahome.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.db.entities.DisabledDetails
import com.dan.jamiicfapp.databinding.RecyclerDisabledInfoBinding
import com.dan.jamiicfapp.ui.jcahome.listerner.PwdRecyclerviewItemclicked


class PwdDetailsAdapter(
    private val recyclerviewItemclicked: PwdRecyclerviewItemclicked,
    private val detailsOfPwds: List<DisabledDetails>,
    private val listerner: (DisabledDetails) -> Unit
) :
    RecyclerView.Adapter<PwdDetailsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_disabled_info,
            parent,
            false
        )
    )

    override fun getItemCount() = detailsOfPwds.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.disabledInfoBinding.pwds = detailsOfPwds[position]

        holder.disabledInfoBinding.buttonDonate.setOnClickListener {
            recyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.disabledInfoBinding.buttonDonate,
                detailsOfPwds[position]
            )
        }

        holder.disabledInfoBinding.textViewNameOfPWD.setOnClickListener {
            recyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.disabledInfoBinding.textViewNameOfPWD,
                detailsOfPwds[position]
            )
            listerner(detailsOfPwds[position])
        }
        holder.disabledInfoBinding.textViewReadMore.setOnClickListener {
            recyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.disabledInfoBinding.textViewReadMore,
                detailsOfPwds[position]
            )
            listerner(detailsOfPwds[position])
        }

    }

    fun deleteItem(pos: Int) {
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(val disabledInfoBinding: RecyclerDisabledInfoBinding) :
        RecyclerView.ViewHolder(disabledInfoBinding.root)


}