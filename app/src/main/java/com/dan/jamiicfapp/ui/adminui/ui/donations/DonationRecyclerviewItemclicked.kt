package com.dan.jamiicfapp.ui.adminui.ui.donations

import android.view.View
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation.DonationList


interface DonationRecyclerviewItemclicked {
    fun OnrecyclerviewItemClicked(view: View, donationList: DonationList)

}