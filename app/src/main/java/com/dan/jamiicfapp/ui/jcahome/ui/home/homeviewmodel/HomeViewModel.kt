package com.dan.jamiicfapp.ui.jcahome.ui.home.homeviewmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.PwdsRepository
import com.dan.jamiicfapp.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class HomeViewModel(private val pwdsRepository: PwdsRepository) : ViewModel() {
    val pwdlist by lazyDeferred {
        pwdsRepository.getAllpwdDetails()
    }


    /**


     */

    suspend fun postCaseVM(
        disabilityCase: RequestBody,
        description: RequestBody,
        amountRequired: RequestBody,
        image: MultipartBody.Part
        //desc: RequestBody,
    ) = withContext(Dispatchers.IO) {
        pwdsRepository.postCaseRepo(
            disabilityCase,
            description,
            amountRequired,
            image

        )
    }
}