package com.dan.jamiicfapp.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity
data class Comment(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("pwd_id")
    val pwdId: String,
    @SerializedName("member_community_id")
    val memberCommunityId: String,
    @SerializedName("comment")
    val comment: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("created_at")
    val createdAt: String
)