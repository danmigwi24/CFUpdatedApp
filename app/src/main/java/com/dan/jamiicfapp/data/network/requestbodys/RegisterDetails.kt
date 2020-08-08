package com.dan.jamiicfapp.data.network.requestbodys


import com.google.gson.annotations.SerializedName

data class RegisterDetails(
    @SerializedName("surname")
    val surname: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("phonenumber")
    val phonenumber: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String
)