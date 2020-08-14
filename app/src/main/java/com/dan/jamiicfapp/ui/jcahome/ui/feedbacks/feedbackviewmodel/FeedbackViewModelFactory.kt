package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.FeedBackRepository

@Suppress("UNCHECKED_CAST")
class FeedbackViewModelFactory(private val feedBackRepository: FeedBackRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FeedbacksViewModel(
            feedBackRepository
        ) as T
    }
}