package com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.PwdsRepository
import com.dan.jamiicfapp.utils.lazyDeferred

class HomeViewModel(private val pwdsRepository: PwdsRepository) : ViewModel() {
    val pwdlist by lazyDeferred {
        pwdsRepository.getAllpwdDetails()
    }
}