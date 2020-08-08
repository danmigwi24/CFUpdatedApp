package com.dan.jamiicfapp.data.network.jcaresponse.donationresponse


import com.google.gson.annotations.SerializedName

data class Donation(
    @SerializedName("pwd_id")
    val pwdId: String,
    @SerializedName("member_community_id")
    val memberCommunityId: String,
    @SerializedName("phonenumber")
    val phonenumber: String,
    @SerializedName("amount_donated")
    val amountDonated: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int
)