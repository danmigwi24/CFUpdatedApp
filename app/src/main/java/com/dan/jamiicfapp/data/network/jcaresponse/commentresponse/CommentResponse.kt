package com.dan.jamiicfapp.data.network.jcaresponse.commentresponse


import com.dan.jamiicfapp.data.db.entities.Comment
import com.google.gson.annotations.SerializedName

data class CommentResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("comment")
    val comment: Comment
)