package com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.RecordcaseRepository

@Suppress("UNCHECKED_CAST")
class RecordcaseViewModelFactory(private val recordcaseRepository: RecordcaseRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return RecordcaseViewModel(
            recordcaseRepository
        ) as T
    }
}