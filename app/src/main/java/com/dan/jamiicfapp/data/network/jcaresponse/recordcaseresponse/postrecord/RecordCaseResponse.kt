package com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.postrecord


import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.postrecord.CaseRecorded
import com.google.gson.annotations.SerializedName

data class RecordCaseResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("caserecorded")
    val caseRecorded: CaseRecorded
)