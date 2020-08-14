package com.dan.jamiicfapp.ui.commentui.cviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.repository.CommentRepository

@Suppress("UNCHECKED_CAST")
class CommentViewModelFactory(
    private val commentRepository: CommentRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CommentViewModel(commentRepository) as T
    }
}