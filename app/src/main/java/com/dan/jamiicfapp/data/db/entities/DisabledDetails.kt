package com.dan.jamiicfapp.data.db.entities


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DisabledDetails(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("surname")
    val surname: String,
    @SerializedName("personal_contact")
    val personalContact: String,
    @SerializedName("family_contact")
    val familyContact: String,
    @SerializedName("id_passport")
    val idPassport: String,
    @SerializedName("dob")
    val dob: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("marital_status")
    val maritalStatus: String,
    @SerializedName("type_of_disability")
    val typeOfDisability: String,
    @SerializedName("help_required")
    val helpRequired: String,
    @SerializedName("disability_description")
    val disabilityDescription: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("amount_required")
    val amountRequired: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable