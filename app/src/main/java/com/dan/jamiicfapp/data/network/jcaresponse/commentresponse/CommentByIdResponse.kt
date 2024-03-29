package com.dan.jamiicfapp.data.network.jcaresponse.commentresponse


import com.google.gson.annotations.SerializedName

data class CommentByIdResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("comment")
    val comment: List<Comment>?
)