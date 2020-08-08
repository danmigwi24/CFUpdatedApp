package com.dan.jamiicfapp.ui.jcahome.ui.feedbacks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FeedbacksViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is FeedBack Fragment"
    }
    val text: LiveData<String> = _text
}