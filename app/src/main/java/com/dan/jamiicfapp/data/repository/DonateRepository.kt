package com.dan.jamiicfapp.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dan.jamiicfapp.data.db.AppDatabase
import com.dan.jamiicfapp.data.db.preference.SessionManager
import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.DonationResponse
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation.DonationList
import com.dan.jamiicfapp.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class DonateRepository(
    private val jcaApiService: JcaApiService,
    private val appDatabase: AppDatabase,
    private val sessionManager: SessionManager
) : SafeApiRequest() {

    suspend fun donatePwd(
        pwd_id: String,
        member_community_id: String,
        phonenumber: String,
        amount_donated: String
    ): DonationResponse {
        return apiRequest {
            jcaApiService.donate(
                pwd_id,
                member_community_id,
                phonenumber,
                amount_donated
            )
        }
    }


    /**
     *
     * Get Donation list
     *
     */

    private val MINIMUM_INTERVAL = 0.5

    private val donationlist = MutableLiveData<List<DonationList>>()

    init {
        donationlist.observeForever {
            savePwd(it)
        }
    }

    suspend fun getAllpwdDetails(): LiveData<List<DonationList>> {
        return withContext(Dispatchers.IO) {
            fetchPwDetails()
            appDatabase.getDonationList().getDonationList()
        }
    }

    private suspend fun fetchPwDetails() {
        val lastSavedAT = sessionManager.fetchTimeStamp2()
        if (lastSavedAT == null || isFetchNeeded(LocalDateTime.parse(lastSavedAT))) {
            //if (lastSavedAT == null) {
            val response = apiRequest { jcaApiService.getDonations() }
            donationlist.postValue(response.donationslist)
        }
    }

    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        //return true
        return ChronoUnit.SECONDS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }


    private fun savePwd(list: List<DonationList>) {
        Coroutines.io {
            sessionManager.saveTimeStamp2(LocalDateTime.now().toString())
            appDatabase.getDonationList().upsertDonationList(list)
        }
    }
}