package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.addDisabiltycaseResponse.DisabilityCaseAddedResponse
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class PwdsRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : SafeApiRequest() {
    //private val MINIMUM_INTERVAL = 0.0333333
    private val MINIMUM_INTERVAL = 0.5

    private val pwds = MutableLiveData<List<DisabledCaseDetails>>()

    init {
        pwds.observeForever {
            savePwd(it)
        }
    }

    suspend fun getAllpwdDetails(): LiveData<List<DisabledCaseDetails>> {
        return withContext(Dispatchers.IO) {
            fetchPwDetails()
            appDatabase.getListOfDisabledDao().getPwd()
        }
    }

    private suspend fun fetchPwDetails() {
        val lastSavedAT = sessionManager.fetchTimeStamp()
        if (lastSavedAT == null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            //if (lastSavedAT == null) {
            val response = apiRequest { jcaApiService.viewpwds() }
            pwds.postValue(response.disabilitycase)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        //return true
        return ChronoUnit.SECONDS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun savePwd(list: List<DisabledCaseDetails>) {
        Coroutines.io {
            sessionManager.saveTimeStamp(LocalDateTime.now().toString())
            appDatabase.getListOfDisabledDao().upsertpwd(list)
        }
    }


    suspend fun postCaseRepo(
        disabilityCase: RequestBody,
        description: RequestBody,
        amountRequired: RequestBody,
        image: MultipartBody.Part
        //desc: RequestBody,

    ): DisabilityCaseAddedResponse {
        return apiRequest {
            jcaApiService.postDisabilityCase(
                //AddDisabilityCase(
                disabilityCase,
                description,
                amountRequired,
                image

                //)
            )

        }
    }


}