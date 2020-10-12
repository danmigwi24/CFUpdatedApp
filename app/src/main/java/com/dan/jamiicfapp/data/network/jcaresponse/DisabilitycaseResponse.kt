package com.dan.jamiicfapp.data.network.jcaresponse


import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.google.gson.annotations.SerializedName

data class DisabilitycaseResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("disabilitycase")
    val disabilitycase: List<DisabledCaseDetails>
)