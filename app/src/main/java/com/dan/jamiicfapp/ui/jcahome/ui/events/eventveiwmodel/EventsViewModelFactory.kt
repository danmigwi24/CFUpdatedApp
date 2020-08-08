package com.dan.jamiicfapp.ui.jcahome.ui.events.eventveiwmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dan.jamiicfapp.data.repository.EventsRepository

@Suppress("UNCHECKED_CAST")
class EventsViewModelFactory(private val eventsRepository: EventsRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return EventsViewModel(
            eventsRepository
        ) as T
    }
}