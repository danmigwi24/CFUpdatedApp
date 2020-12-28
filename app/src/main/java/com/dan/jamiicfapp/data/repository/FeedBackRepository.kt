package com.dan.jamiicfapp.data.repository

import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.commentresponse.CommentResponse
import com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse.FeedbackResponse
import com.dan.jamiicfapp.data.network.jcaresponse.feedbackresponse.GetFeedbackResponseAll

class FeedBackRepository(private val jcaApiService: JcaApiService) : SafeApiRequest() {

    suspend fun feedbackRepo(member_community_id: String, feedback: String): FeedbackResponse {
        return apiRequest { jcaApiService.feedbackApi(member_community_id, feedback) }
    }

    suspend fun commentInRepo(): GetFeedbackResponseAll {
        return apiRequest { jcaApiService.getFeeback() }
    }

}