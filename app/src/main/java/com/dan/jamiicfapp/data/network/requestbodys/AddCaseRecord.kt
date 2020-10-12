package com.dan.jamiicfapp.data.network.requestbodys


import com.google.gson.annotations.SerializedName

data class AddCaseRecord(
    @SerializedName("member_community_id")
    val memberCommunityId: String,
    @SerializedName("disability_case")
    val disabilityCase: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("disabled_person_name")
    val disabledPersonName: String,
    @SerializedName("type_of_disability")
    val typeOfDisability: String,
    @SerializedName("is_approved")
    val isApproved: String
)