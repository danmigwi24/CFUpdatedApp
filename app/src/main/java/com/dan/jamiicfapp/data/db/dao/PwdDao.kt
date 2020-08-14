package com.dan.jamiicfapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dan.jamiicfapp.data.db.entities.DisabledDetails

@Dao
interface PwdDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertpwd(disabledDetails: List<DisabledDetails>)

    @Query("SELECT * FROM disableddetails")
    fun getPwd(): LiveData<List<DisabledDetails>>


}