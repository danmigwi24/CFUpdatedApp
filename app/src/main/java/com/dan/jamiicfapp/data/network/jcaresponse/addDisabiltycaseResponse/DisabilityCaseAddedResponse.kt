package com.dan.jamiicfapp.data.network.jcaresponse.addDisabiltycaseResponse


import com.google.gson.annotations.SerializedName

data class DisabilityCaseAddedResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String?,
    @SerializedName("disabilitycase")
    val disabilitycase: Disabilitycase?
)