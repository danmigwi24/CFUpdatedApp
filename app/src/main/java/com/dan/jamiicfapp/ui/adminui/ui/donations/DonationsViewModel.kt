package com.dan.jamiicfapp.ui.adminui.ui.donations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.DonateRepository
import com.dan.jamiicfapp.utils.lazyDeferred

class DonationsViewModel(private val donateRepository: DonateRepository) : ViewModel() {

    val donationlist by lazyDeferred {
     donateRepository.getAllpwdDetails()
    }
}