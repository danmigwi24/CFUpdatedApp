package com.dan.jamiicfapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dan.jamiicfapp.data.network.jcaresponse.donationresponse.getdonation.DonationList

@Dao
interface DonationListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertDonationList(disabledDetails: List<DonationList>)

    @Query("SELECT * FROM donationlist")
    fun getDonationList(): LiveData<List<DonationList>>


}