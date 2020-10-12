package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.postrecord.RecordCaseResponse
import com.dan.jamiicfapp.data.network.requestbodys.AddCaseRecord
import com.dan.jamiicfapp.data.network.requestbodys.AddCaseRecordApprove
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class RecordcaseRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : SafeApiRequest() {

    suspend fun recordcaseRepo(
        memberCommunityId: String,
        disabilityCase: String,
        description: String,
        location: String,
        disabledPersonName: String,
        typeOfDisability: String,
        isApproved: String
    ): RecordCaseResponse {
        return apiRequest {
            jcaApiService.storeRecordedcase(
                AddCaseRecord(
                    memberCommunityId,
                    disabilityCase,
                    description,
                    location,
                    disabledPersonName,
                    typeOfDisability,
                    isApproved
                )
            )

        }
    }

    suspend fun updateRecordCaseRepo(
        recordId: Int,
        isApproved: String
    ): RecordCaseResponse {
        return apiRequest {
            jcaApiService.updateRecordedcase(
                recordId,
                AddCaseRecordApprove(
                    isApproved
                )
            )

        }
    }


    private val MINIMUM_INTERVAL = 2

    private val caserecorded = MutableLiveData<List<RecordedCase>>()

    init {
        caserecorded.observeForever {
            saveRecordedCase(it)
        }
    }

    suspend fun getAllRecordedCases(): LiveData<List<RecordedCase>> {
        return withContext(Dispatchers.IO) {
            fetchRecordedCases()
            appDatabase.getListOfCaseRecorded().getcaserecorded()
        }
    }

    private suspend fun fetchRecordedCases() {
        val lastSavedAT = sessionManager.CasesfetchTimeStamp()
        if (lastSavedAT == null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            //if (lastSavedAT == null) {
            val response = apiRequest { jcaApiService.getRecordedcases() }
            caserecorded.postValue(response.recordedCases)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        //return true
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun saveRecordedCase(list: List<RecordedCase>) {
        Coroutines.io {
            sessionManager.CasessaveTimeStamp(LocalDateTime.now().toString())
            appDatabase.getListOfCaseRecorded().upsertcaserecorded(list)
        }
    }

}