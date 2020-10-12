package com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class RecordedCase(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("member_community_id")
    val memberCommunityId: Int,
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
    val isApproved: String?,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable