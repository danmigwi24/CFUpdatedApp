package com.dan.jamiicfapp.data.network.jcaresponse.donationresponse


import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.Donation
import com.google.gson.annotations.SerializedName

data class DonationResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("donation")
    val donation: Donation
)