package com.dan.jamiicfapp.ui.jcahome.listerner

import android.view.View
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.data.db.entities.JcaEvent


interface EventRecyclerviewItemclicked {
    fun OnrecyclerviewItemClicked(view: View, jcaEvent: JcaEvent)

}