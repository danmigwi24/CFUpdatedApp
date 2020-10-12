package com.dan.jamiicfapp.data.network.jcaresponse.addDisabiltycaseResponse


import com.google.gson.annotations.SerializedName

data class Disabilitycase(
    @SerializedName("disability_case")
    val disabilityCase: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("amount_required")
    val amountRequired: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int
)