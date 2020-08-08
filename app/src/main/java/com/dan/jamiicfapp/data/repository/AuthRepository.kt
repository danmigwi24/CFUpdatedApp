package com.dan.jamiicfapp.data.repository

import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.entities.User
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.AuthResponse
import com.dan.jamiicfapp.data.network.requestbodys.RegisterDetails

class AuthRepository(private val authApi: JcaApiService, private val appDatabase: AppDatabase) :
    SafeApiRequest() {
    //SERVER SIDE
    suspend fun loginRepo(phonenumber: String, password: String): AuthResponse {
        return apiRequest { authApi.login_Api(phonenumber, password) }
    }

    suspend fun registerUser(
        surname: String,
        name: String,
        phonenumber: String,
        address: String,
        email: String,
        password: String,
        password_confirmation: String
    ): AuthResponse {
        return apiRequest {
            authApi.registerUser(
                RegisterDetails(
                    surname,
                    name,
                    phonenumber,
                    address,
                    email,
                    password,
                    password_confirmation
                )
            )
        }
    }


    //SAVING USER IN LOCAL DATABASE ie ROOMDB
    suspend fun saveUserInRoom(user: User) = appDatabase.getUserLoggedInDao().upsert(user)

    fun getUserInRoom() = appDatabase.getUserLoggedInDao().getuser()
}