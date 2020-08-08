package com.dan.jamiicfapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dan.jamiicfapp.data.db.entities.DisabledDetails
import com.dan.jamiicfapp.data.db.entities.User
import com.dan.jamiicfapp.data.db.entities.JcaEvent

@Database(entities = [User::class, DisabledDetails::class, JcaEvent::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserLoggedInDao(): UserDao
    abstract fun getListOfDisabledDao(): PwdDao
    abstract fun getListOfEventsDao(): EventsDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private var lock = Any()

        operator fun invoke(context: Context) =
            instance ?: synchronized(
                lock
            ) {
                instance
                    ?: buildDatabase(
                        context
                    ).also {
                        instance = it
                    }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context,
                AppDatabase::class.java, "jca_db"
            ).build()

    }
}