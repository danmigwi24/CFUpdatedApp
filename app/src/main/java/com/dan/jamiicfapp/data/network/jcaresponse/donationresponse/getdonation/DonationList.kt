package com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation


import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class DonationList(
    @SerializedName("id")
    val id: Int,
    @SerializedName("dcase_foreignid")
    val dcaseForeignid: Int,
    @SerializedName("member_community_id")
    val memberCommunityId: Int,
    @SerializedName("phonenumber")
    val phonenumber: String,
    @SerializedName("amount_donated")
    val amountDonated: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("updated_at")
    val updatedAt: String
):Parcelable