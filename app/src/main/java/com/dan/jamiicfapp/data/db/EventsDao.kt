package com.dan.jamiicfapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dan.jamiicfapp.data.db.entities.JcaEvent

@Dao
interface EventsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertEvents(disabledDetails: List<JcaEvent>)

    @Query("SELECT * FROM jcaevent")
    fun getEvents(): LiveData<List<JcaEvent>>


}