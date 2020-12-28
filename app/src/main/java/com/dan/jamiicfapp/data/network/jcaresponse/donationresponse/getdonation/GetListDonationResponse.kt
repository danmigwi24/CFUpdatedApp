package com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation


import com.google.gson.annotations.SerializedName

data class GetListDonationResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("donations")
    val donationslist: List<DonationList>
)