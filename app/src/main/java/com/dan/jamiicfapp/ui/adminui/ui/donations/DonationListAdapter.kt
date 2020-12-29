package com.dan.jamiicfapp.ui.adminui.ui.donations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation.DonationList
import com.dan.jamiicfapp.databinding.RecyclerDonationlistBinding


class DonationListAdapter(
    private val recyclerviewItemclicked: DonationRecyclerviewItemclicked,
    private val donationlist: List<DonationList>,
    private val listerner: (DonationList) -> Unit
) :
    RecyclerView.Adapter<DonationListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.recycler_donationlist,
            parent,
            false
        )
    )

    override fun getItemCount() = donationlist.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.donationlist = donationlist[position]

        holder.binding.buttonDonate.setOnClickListener {
            recyclerviewItemclicked.OnrecyclerviewItemClicked(
                holder.binding.buttonDonate,
                donationlist[position]
            )
        }

//        holder.binding.textViewNameOfPWD.setOnClickListener {
//            recyclerviewItemclicked.OnrecyclerviewItemClicked(
//                holder.binding.textViewNameOfPWD,
//                donationlist[position]
//            )
//            listerner(donationlist[position])
//        }
//        holder.binding.textViewReadMore.setOnClickListener {
//            recyclerviewItemclicked.OnrecyclerviewItemClicked(
//                holder.binding.textViewReadMore,
//                donationlist[position]
//            )
//            listerner(donationlist[position])
//        }


    }

    fun deleteItem(pos: Int) {
        notifyItemRemoved(pos)
    }

    inner class ViewHolder(val binding: RecyclerDonationlistBinding) :
        RecyclerView.ViewHolder(binding.root)


}