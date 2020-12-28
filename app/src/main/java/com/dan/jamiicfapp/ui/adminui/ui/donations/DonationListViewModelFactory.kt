package com.dan.jamiicfapp.ui.adminui.ui.donations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.DonateRepository
import com.dan.jamiicfapp.data.repository.PwdsRepository

@Suppress("UNCHECKED_CAST")
class DonationListViewModelFactory(private val donateRepository: DonateRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DonationsViewModel(
            donateRepository
        ) as T
    }
}