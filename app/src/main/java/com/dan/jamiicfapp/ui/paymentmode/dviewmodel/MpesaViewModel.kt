package com.dan.jamiicfapp.ui.paymentmode.dviewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.DonateRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MpesaViewModel(private val donateRepository: DonateRepository) : ViewModel() {

    suspend fun donatePwdVm(
        pwd_id: String,
        member_community_id: String,
        phonenumber: String,
        amount_donated: String
    ) = withContext(Dispatchers.IO) {
        donateRepository.donatePwd(pwd_id, member_community_id, phonenumber, amount_donated)
    }

}