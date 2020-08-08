package com.dan.jamiicfapp.data.network.jcaresponse


import com.dan.jamiicfapp.data.db.entities.DisabledDetails
import com.google.gson.annotations.SerializedName

data class PwdResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("code")
    val code: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("user")
    val pwdDetailsInServer: List<DisabledDetails>
)