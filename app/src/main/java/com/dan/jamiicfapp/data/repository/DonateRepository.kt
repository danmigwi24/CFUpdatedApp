package com.dan.jamiicfapp.data.repository

import com.dan.jamiicfapp.data.network.JcaApiService
import com.dan.jamiicfapp.data.network.SafeApiRequest
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.DonationResponse

class DonateRepository(private val jcaApiService: JcaApiService) : SafeApiRequest() {

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
}