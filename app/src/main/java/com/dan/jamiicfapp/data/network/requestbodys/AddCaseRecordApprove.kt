package com.dan.jamiicfapp.data.network.requestbodys


import com.google.gson.annotations.SerializedName

data class AddCaseRecordApprove(
    @SerializedName("is_approved")
    val isApproved: String
)