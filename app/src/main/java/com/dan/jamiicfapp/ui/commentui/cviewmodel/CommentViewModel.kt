package com.dan.jamiicfapp.ui.commentui.cviewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.repository.CommentRepository
import com.dan.jamiicfapp.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentViewModel(
    private val commentRepository: CommentRepository
) : ViewModel() {

    suspend fun giveCommentVM(pwd_id: String, member_community_id: String, feedback: String) =
        withContext(Dispatchers.IO) {
            commentRepository.commentInRepo(pwd_id, member_community_id, feedback)
        }


    val getCommentsInVM by lazyDeferred {
        commentRepository.getCommentsInRepo()
    }


}