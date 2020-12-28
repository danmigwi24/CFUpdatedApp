package com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse

import com.google.gson.annotations.SerializedName

data class GetFeedbackResponseAll(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("feedback")
    val feedback:  List<Feedback>?
)