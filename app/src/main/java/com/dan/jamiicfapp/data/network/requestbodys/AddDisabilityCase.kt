package com.dan.jamiicfapp.data.network.requestbodys


import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import retrofit2.http.Multipart

data class AddDisabilityCase(
    @SerializedName("disability_case")
    val disabilityCase: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: MultipartBody.Part,
    /*@SerializedName("image_url")
    val imageUrl: String,*/
    @SerializedName("amount_required")
    val amountRequired: String
)