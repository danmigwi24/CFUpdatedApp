package com.dan.jamiicfapp.data.network.jcaresponse


import com.google.gson.annotations.SerializedName

data class Errors(
    @SerializedName("surname")
    val surname: List<String>,
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("phonenumber")
    val phonenumber: List<String>,
    @SerializedName("address")
    val address: List<String>,
    @SerializedName("email")
    val email: List<String>,
    @SerializedName("password")
    val password: List<String>
)