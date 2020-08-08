package com.dan.jamiicfapp.data.network.requestbodys


import com.google.gson.annotations.SerializedName

data class DonateRequest(
    @SerializedName("pwd_id")
    val pwdId: String,
    @SerializedName("member_community_id")
    val memberCommunityId: String,
    @SerializedName("phonenumber")
    val phonenumber: String,
    @SerializedName("amount_donated")
    val amountDonated: String
)