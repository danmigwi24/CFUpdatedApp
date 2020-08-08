package com.dan.jamiicfapp.data.network.jcaresponse.eventresponse


import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.google.gson.annotations.SerializedName

data class EventsResponse(
    @SerializedName("isSuccessful")
    val isSuccessful: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("events")
    val events: List<JcaEvent>
)