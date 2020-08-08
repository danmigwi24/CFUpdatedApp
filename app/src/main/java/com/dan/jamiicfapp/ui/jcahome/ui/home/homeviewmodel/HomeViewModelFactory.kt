package com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.PwdsRepository

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val pwdsRepository: PwdsRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(
            pwdsRepository
        ) as T
    }
}