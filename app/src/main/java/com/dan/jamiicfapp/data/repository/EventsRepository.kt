package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EventsRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase
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
            appDatabase.getListOfEventsDao().upsertEvents(eventlist)
        }

    }

    /**
     *Fetch data from server or API
     */

    private suspend fun fetchEventFromApi() {
        if (isFetchNeeded()) {
            val response = apiRequest { jcaApiService.viewEvents() }
            event.postValue(response.events)
        }
    }

    private fun isFetchNeeded(): Boolean {
        return true
    }

    suspend fun getEventsInRepo(): LiveData<List<JcaEvent>> {
        return withContext(Dispatchers.IO) {
            fetchEventFromApi()
            appDatabase.getListOfEventsDao().getEvents()
        }
    }
}