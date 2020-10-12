package com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.postrecord


import com.google.gson.annotations.SerializedName

data class CaseRecorded(
    @SerializedName("id")
    val id: Int,
    @SerializedName("member_community_id")
    val memberCommunityId: Int,
    @SerializedName("disability_case")
    val disabilityCase: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("location")
    val location: String?,
    @SerializedName("disabled_person_name")
    val disabledPersonName: String?,
    @SerializedName("type_of_disability")
    val typeOfDisability: String?,
    @SerializedName("is_approved")
    val isApproved: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("updated_at")
    val updatedAt: String?
)