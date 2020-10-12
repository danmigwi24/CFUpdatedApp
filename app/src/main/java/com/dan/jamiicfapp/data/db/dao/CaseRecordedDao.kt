package com.dan.jamiicfapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase

@Dao
interface CaseRecordedDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertcaserecorded(disabledDetails: List<RecordedCase>)

    @Query("SELECT * FROM recordedcase")
    fun getcaserecorded(): LiveData<List<RecordedCase>>


}