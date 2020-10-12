package com.dan.jamiicfapp.ui.paymentmode.dviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.DonateRepository

@Suppress("UNCHECKED_CAST")
class MpesaViewModelProvider(private val donateRepository: DonateRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MpesaViewModel(donateRepository) as T
    }
}