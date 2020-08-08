package com.dan.jamiicfapp.ui.jcahome.ui.events.eventveiwmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.EventsRepository
import com.dan.jamiicfapp.utils.lazyDeferred


class EventsViewModel(private val eventsRepository: EventsRepository) : ViewModel() {

    val getEventsInVM by lazyDeferred {
        eventsRepository.getEventsInRepo()
    }


}