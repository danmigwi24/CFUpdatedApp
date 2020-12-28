package com.dan.jamiicfapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dan.jamiicfapp.data.db.dao.*
import com.dan.jamiicfapp.data.network.jcaresponse.commentresponse.Comment
import com.dan.jamiicfapp.data.db.entities.DisabledCaseDetails
import com.dan.jamiicfapp.data.db.entities.JcaEvent
import com.dan.jamiicfapp.data.db.entities.User
import com.dan.jamiicfapp.data.network.jcaresponse.recordcaseresponse.getrecord.RecordedCase

@Database(
    entities = [User::class, DisabledCaseDetails::class, JcaEvent::class, Comment::class, RecordedCase::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getUserLoggedInDao(): UserDao
    abstract fun getListOfDisabledDao(): PwdDao
    abstract fun getListOfEventsDao(): EventsDao
    abstract fun getListOfCommentDao(): CommentsDao
    abstract fun getListOfCaseRecorded(): CaseRecordedDao

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