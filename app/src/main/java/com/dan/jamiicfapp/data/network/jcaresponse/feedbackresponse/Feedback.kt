package com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse


import com.google.gson.annotations.SerializedName

data class Feedback(
    @SerializedName("member_community_id")
    val memberCommunityId: String,
    @SerializedName("feedback")
    val feedback: String?,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int
)