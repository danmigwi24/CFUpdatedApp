package com.dan.jamiicfapp.ui.adminui.ui.donations

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DonationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Donations"
    }
    val text: LiveData<String> = _text
}