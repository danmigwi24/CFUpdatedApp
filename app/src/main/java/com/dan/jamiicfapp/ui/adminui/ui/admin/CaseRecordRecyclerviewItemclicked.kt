package com.dan.jamiicfapp.ui.adminui.ui.admin

import android.view.View
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase


interface CaseRecordRecyclerviewItemclicked {
    fun OnrecyclerviewItemClicked(view: View, caseRecorded: RecordedCase)

}