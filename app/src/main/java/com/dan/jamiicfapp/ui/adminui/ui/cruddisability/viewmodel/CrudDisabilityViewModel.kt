package com.dan.jamiicfapp.ui.adminui.ui.cruddisability.viewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.PwdsRepository
import com.dan.jamiicfapp.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CrudDisabilityViewModel(private val pwdsRepository: PwdsRepository) : ViewModel() {

    /**
     *View list of disability case
     */
    val pwdlist by lazyDeferred {
        pwdsRepository.getAllpwdDetails()
    }


    /**
     *Edit disability case
     */

    suspend fun updateCaseVM(
        updateId: Int,
        disabilityCase: RequestBody,
        description: RequestBody,
        amountRequired: RequestBody,
        image: MultipartBody.Part
        //desc: RequestBody,
    ) = withContext(Dispatchers.IO) {
        pwdsRepository.updateCaseRepo(
            updateId,
            disabilityCase,
            description,
            amountRequired,
            image

        )
    }
}