package com.dan.jamiicfapp.ui.commentui.cviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.db.entities.Comment
import com.dan.jamiicfapp.data.repository.CommentRepository
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CommentViewModel(
    private val commentRepository: CommentRepository
) : ViewModel() {

    suspend fun giveCommentVM(pwd_id: String, member_community_id: String, feedback: String) =
        withContext(Dispatchers.IO) {
            commentRepository.commentInRepo(pwd_id, member_community_id, feedback)
        }

    private val _getCommentsInVM = MutableLiveData<List<Comment>>()
    val getCommentsInVM: LiveData<List<Comment>>
        get() = _getCommentsInVM

     fun getComment() {
        Coroutines.io {
            val comRepo = commentRepository.getCommentsInRepo().comment
            Coroutines.main {
                _getCommentsInVM.postValue(comRepo)
            }


        }
    }
//    val getCommentsInVM by lazyDeferred {
//        commentRepository.getCommentsInRepo()
//    }


}