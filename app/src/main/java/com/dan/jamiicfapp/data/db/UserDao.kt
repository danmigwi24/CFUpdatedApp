package com.dan.jamiicfapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dan.jamiicfapp.data.db.entities.CURRENT_USER_ID
import com.dan.jamiicfapp.data.db.entities.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(vararg user: User)

    @Query("SELECT * FROM user WHERE uid=$CURRENT_USER_ID")
    fun getuser(): LiveData<User>

    @Delete
    fun delete(user: User)
}