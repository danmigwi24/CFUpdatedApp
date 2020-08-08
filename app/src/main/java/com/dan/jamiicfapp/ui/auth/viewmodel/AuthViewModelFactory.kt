package com.dan.jamiicfapp.ui.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.AuthRepository

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val loginRepository: AuthRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(loginRepository) as T
    }
}