package com.dan.jamiicfapp.ui.jcahome.listerner

import android.view.View
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails



interface PwdRecyclerviewItemclicked {
    fun OnrecyclerviewItemClicked(view: View, disabledDetails: DisabledCaseDetails)

}