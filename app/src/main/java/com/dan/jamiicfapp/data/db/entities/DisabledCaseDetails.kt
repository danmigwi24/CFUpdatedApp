package com.dan.jamiicfapp.data.db.entities


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
@Entity
data class DisabledCaseDetails(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val disabilitycaseId: Int,
    @SerializedName("disability_case")
    val disabilityCase: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("image_url")
    val image_url: String,
    @SerializedName("amount_required")
    val amount_required: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable
