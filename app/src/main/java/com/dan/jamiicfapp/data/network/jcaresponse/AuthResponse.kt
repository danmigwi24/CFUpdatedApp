package com.dan.jamiicfapp.data.network.jcaresponse


import com.dan.jamiicfapp.data.db.entities.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("success")
    val success: Boolean?,
    @SerializedName("code")
    val code: Int?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("user")
    val user: User?,
    @SerializedName("errors")
    val errors: Errors?

)