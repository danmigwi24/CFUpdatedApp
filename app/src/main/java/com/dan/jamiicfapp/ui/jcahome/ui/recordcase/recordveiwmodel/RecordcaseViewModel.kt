package com.dan.jamiicfapp.ui.jcahome.ui.recordcase.recordveiwmodel

import androidx.lifecycle.ViewModel
import com.dan.jamiicfapp.data.repository.RecordcaseRepository
import com.dan.jamiicfapp.utils.lazyDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RecordcaseViewModel(private val recordcaseRepository: RecordcaseRepository) : ViewModel() {

    suspend fun recordcaseVM(
        memberCommunityId: String,
        disabilityCase: String,
        description: String,
        location: String,
        disabledPersonName: String,
        typeOfDisability: String,
        isApproved: String
    ) = withContext(Dispatchers.IO) {
        recordcaseRepository.recordcaseRepo(
            memberCommunityId,
            disabilityCase,
            description,
            location,
            disabledPersonName,
            typeOfDisability,
            isApproved
        )
    }

    suspend fun updateRecordCase(
        recordId: Int,
        isApproved: String

    ) = withContext(Dispatchers.IO) {
        recordcaseRepository.updateRecordCaseRepo(
            recordId,
            isApproved
        )

    }


    val recordedcasesVM by lazyDeferred {
        recordcaseRepository.getAllRecordedCases()
    }
}