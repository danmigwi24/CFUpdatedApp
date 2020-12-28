package com.dan.jamiicfapp.data.network.jcaresponse.editdisability


import com.google.gson.annotations.SerializedName

data class UpdateDisabilityResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("disabilitycase")
    val disabilitycase: Boolean
)