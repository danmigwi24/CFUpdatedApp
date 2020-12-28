package com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.PwdsRepository

@Suppress("UNCHECKED_CAST")
class CrudDisabilityViewModelFactory(private val pwdsRepository: PwdsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CrudDisabilityViewModel(
            pwdsRepository
        ) as T
    }
}