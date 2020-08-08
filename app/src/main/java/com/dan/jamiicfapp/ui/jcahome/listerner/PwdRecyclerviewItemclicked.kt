package com.dan.jamiicfapp.ui.jcahome.listerner

import android.view.View
import com.dan.jamiicfapp.data.db.entities.DisabledDetails


interface PwdRecyclerviewItemclicked {
    fun OnrecyclerviewItemClicked(view: View, disabledDetails: DisabledDetails)

}