package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks.feedbackviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse.Feedback
import com.dan.jamiicfapp.data.repository.FeedBackRepository
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FeedbacksViewModel(private val feedBackRepository: FeedBackRepository) : ViewModel() {

    suspend fun giveFeedbackVM(member_community_id: String, feedback: String) =
        withContext(Dispatchers.IO) { feedBackRepository.feedbackRepo(member_community_id, feedback)
        }


    //Get All Feedbacks
    private val _getCommentsInVM = MutableLiveData<List<Feedback>>()
    val fetchFeedbackInVM: LiveData<List<Feedback>>
        get() = _getCommentsInVM

    fun fetchFeedback() {
        Coroutines.io {
            val comRepo = feedBackRepository.commentInRepo().feedback
            Coroutines.main {
                _getCommentsInVM.postValue(comRepo)
            }


        }
    }




}