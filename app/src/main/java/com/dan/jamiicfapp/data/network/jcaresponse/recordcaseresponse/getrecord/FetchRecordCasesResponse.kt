package com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord


import com.google.gson.annotations.SerializedName

data class FetchRecordCasesResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("caserecorded")
    val recordedCases: List<RecordedCase>
)