package com.dan.jamiicfapp.data.network.requestbodys


import com.google.gson.annotations.SerializedName

data class LoginDetails(
    @SerializedName("phonenumber")
    val phonenumber: String,
    @SerializedName("password")
    val password: String
)