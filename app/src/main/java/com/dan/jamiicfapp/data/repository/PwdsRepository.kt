package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.entities.DisabledDetails
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.utils.Coroutines
import com.dan.jamiicfapp.data.db.preference.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class PwdsRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : SafeApiRequest() {
    private val MINIMUM_INTERVAL = 6

    private val pwds = MutableLiveData<List<DisabledDetails>>()

    init {
        pwds.observeForever {
            savePwd(it)
        }
    }

    suspend fun getAllpwdDetails(): LiveData<List<DisabledDetails>> {
        return withContext(Dispatchers.IO) {
            fetchPwDetails()
            appDatabase.getListOfDisabledDao().getPwd()
        }
    }

    private suspend fun fetchPwDetails() {
        val lastSavedAT = sessionManager.fetchTimeStamp()
        if (lastSavedAT == null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            val response = apiRequest { jcaApiService.viewpwds() }
            pwds.postValue(response.pwdDetailsInServer)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun savePwd(list: List<DisabledDetails>) {
        Coroutines.io {
            sessionManager.saveTimeStamp(LocalDateTime.now().toString())
            appDatabase.getListOfDisabledDao().upsertpwd(list)
        }
    }

}