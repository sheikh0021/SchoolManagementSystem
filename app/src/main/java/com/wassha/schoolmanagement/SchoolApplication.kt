package com.wassha.schoolmanagement

import android.app.Application
import androidx.room.Room
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.repository.AuthRepositoryImpl

class SchoolApplication : Application() {

    lateinit var db: SchoolDatabase
    lateinit var authRepo: AuthRepositoryImpl

    override fun onCreate() {
        super.onCreate()
//create room database
        db = Room.databaseBuilder(
            applicationContext,
            SchoolDatabase::class.java,
            "school_db"
        )
        .fallbackToDestructiveMigration() // Recreates DB on any schema change (for development)
        .build()
//Pass Dao's (not the database)
        authRepo = AuthRepositoryImpl(
            studentDao = db.studentDao(),
            teacherDao = db.teacherDao()
        )
    }
}
