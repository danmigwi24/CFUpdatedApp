package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.FeedBackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedbacksViewModel(private val feedBackRepository: FeedBackRepository) : ViewModel() {

    suspend fun giveFeedbackVM(member_community_id: String, feedback: String) =
        withContext(Dispatchers.IO) { feedBackRepository.feedbackRepo(member_community_id, feedback)
        }


}