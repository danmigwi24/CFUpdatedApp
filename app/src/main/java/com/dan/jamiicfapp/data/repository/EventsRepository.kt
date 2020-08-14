package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.ui.auth.LoginActivity
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class EventsRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : SafeApiRequest() {

    /**
     *Fetch data from local database that is roomDb
     */
    private val event = MutableLiveData<List<JcaEvent>>()

    init {
        event.observeForever {
            saveEventsInRoom(it)
        }
    }

    private fun saveEventsInRoom(eventlist: List<JcaEvent>) {
        Coroutines.io {
            sessionManager.saveTimeStamp2(LocalDateTime.now().toString())
            appDatabase.getListOfEventsDao().upsertEvents(eventlist)
        }

    }

    /**
     *Fetch data from server or API
     */

    private suspend fun fetchEventFromApi() {
        val lastSavedAT = sessionManager.fetchTimeStamp2()
        if (lastSavedAT==null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            val response = apiRequest { jcaApiService.viewEvents() }
            event.postValue(response.events)
        }
    }

    private fun isFetchNeeded(saveAT:LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(saveAT,LocalDateTime.now())>6
    }

    suspend fun getEventsInRepo(): LiveData<List<JcaEvent>> {
        return withContext(Dispatchers.IO) {
            fetchEventFromApi()
            appDatabase.getListOfEventsDao().getEvents()
        }
    }
}