package com.dan.jamiicfapp.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dan.jamiicfapp.data.db.entities.Comment
import com.dan.jamiicfapp.data.db.entities.JcaEvent

@Dao
interface CommentsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertComment(listofcomments: List<Comment>)

    @Query("SELECT * FROM comment ")
    fun getComment(): LiveData<List<Comment>>
}