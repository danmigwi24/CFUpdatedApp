package com.dan.jamiicfapp.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.db.entities.User
import com.dan.jamiicfapp.data.repository.AuthRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthViewModel(private val loginRepository: AuthRepository) : ViewModel() {

    suspend fun saveUserVmRoom(user: User) = loginRepository.saveUserInRoom(user)

    fun getUserVmRoom() = loginRepository.getUserInRoom()

    suspend fun loginUser(phonenumber: String, password: String) =
        withContext(Dispatchers.IO) { loginRepository.loginRepo(phonenumber, password) }

    suspend fun registerUser(
        surname: String,
        name: String,
        phonenumber: String,
        address: String,
        email: String,
        password: String,
        password_confirmation: String
    ) =
        withContext(Dispatchers.IO) {
            loginRepository.registerUser(
                surname,
                name,
                phonenumber,
                address,
                email,
                password,
                password_confirmation
            )
        }

}