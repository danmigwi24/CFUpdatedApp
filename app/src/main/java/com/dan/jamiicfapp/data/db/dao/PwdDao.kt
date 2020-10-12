package com.dan.jamiicfapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails

@Dao
interface PwdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertpwd(disabledDetails: List<DisabledCaseDetails>)

    @Query("SELECT * FROM disabledcasedetails")
    fun getPwd(): LiveData<List<DisabledCaseDetails>>


}