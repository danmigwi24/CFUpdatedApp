package com.dan.jamiicfapp.data.repository

import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.commentresponse.CommentResponse

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

    suspend fun getCommentsInRepo() =
        apiRequest { jcaApiService.getCommentByPwdID(sessionManager.fetchPwdId().toString()) }

/*
    */
    /**
     *Fetch data from local database that is roomDb
     *//*
    private val comment = MutableLiveData<List<Comment>>()

    init {
        comment.observeForever {
            saveEventsInRoom(it)
        }
    }

    private fun saveEventsInRoom(comments: List<Comment>) {
        Coroutines.io {
            sessionManager.CommentsaveTimeStamp(LocalDateTime.now().toString())
            appDatabase.getListOfCommentDao().upsertComment(comments)
        }

    }

    */
    /**
     *Fetch data from server or API
     *//*

    private suspend fun fetchEventFromApi() {
        val lastSavedAT = sessionManager.CommentfetchTimeStamp()
        if (lastSavedAT == null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            val response = apiRequest {
                jcaApiService.getCommentByPwdID(
                    sessionManager.fetchPwdId().toString()
                )
            }
            comment.postValue(response.comment)
        }
    }

    private fun isFetchNeeded(saveAT: LocalDateTime): Boolean {
        return true
        //return ChronoUnit.SECONDS.between(saveAT, LocalDateTime.now()) > 2
    }

    suspend fun getCommentsInRepo(): LiveData<List<Comment>> {
        return withContext(Dispatchers.IO) {
            fetchEventFromApi()
            appDatabase.getListOfCommentDao().getComment()
        }
    }*/
}
