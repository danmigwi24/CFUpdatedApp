package com.dan.jamiicfapp.data.db.entities


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class JcaEvent(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    val id: Int,
    @SerializedName("event_title")
    val eventTitle: String,
    @SerializedName("event_venue")
    val eventVenue: String,
    @SerializedName("event_date")
    val eventDate: String,
    @SerializedName("event_start_time")
    val eventStartTime: String,
    @SerializedName("event_ending_time")
    val eventEndingTime: String,
    @SerializedName("event_description")
    val eventDescription: String,
    @SerializedName("event_image")
    val eventImage: String,
    @SerializedName("event_imageurl")
    val eventImageurl: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
) : Parcelable