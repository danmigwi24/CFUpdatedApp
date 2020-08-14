package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.entities.Comment
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.commentresponse.CommentResponse
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class CommentRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : SafeApiRequest() {

    suspend fun commentInRepo(
        pwd_id: String,
        member_community_id: String,
        comment: String
    ): CommentResponse {
        return apiRequest { jcaApiService.comment(pwd_id, member_community_id, comment) }
    }


    /**
     *Fetch data from local database that is roomDb
     */
    private val comment = MutableLiveData<List<Comment>>()

    init {
        comment.observeForever {
            saveEventsInRoom(it)
        }
    }

    private fun saveEventsInRoom(comments: List<Comment>) {
        Coroutines.io {
            sessionManager.saveTimeStamp2(LocalDateTime.now().toString())
            appDatabase.getListOfCommentDao().upsertComment(comments)
        }

    }

    /**
     *Fetch data from server or API
     */

    private suspend fun fetchEventFromApi() {
        val lastSavedAT = sessionManager.fetchTimeStamp2()
        if (lastSavedAT == null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            val response = apiRequest { jcaApiService.getCommentByPwdID() }
            comment.postValue(response.comment)
        }
    }

    private fun isFetchNeeded(saveAT: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(saveAT, LocalDateTime.now()) > 6
    }

    suspend fun getCommentsInRepo(): LiveData<List<Comment>> {
        return withContext(Dispatchers.IO) {
            fetchEventFromApi()
            appDatabase.getListOfCommentDao().getComment()
        }
    }
}
