package com.dan.jamiicfapp.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_USER_ID=0

@Entity
data class User(
    @SerializedName("id")
    val id: Int, // 1
    @SerializedName("surname")
    val surname: String, // Mwangi
    @SerializedName("name")
    val name: String, // Morry Kamau
    @SerializedName("phonenumber")
    val phonenumber: String, // 0798997940
    @SerializedName("address")
    val address: String, // Gilgil
    @SerializedName("email")
    val email: String, // morry@gmail.com
    @SerializedName("created_at")
    val createdAt: String, // 2020-07-30 16:25:22
    @SerializedName("updated_at")
    val updatedAt: String // 2020-07-30 16:25:22
){
    @PrimaryKey(autoGenerate = false)
    var uid =CURRENT_USER_ID
}