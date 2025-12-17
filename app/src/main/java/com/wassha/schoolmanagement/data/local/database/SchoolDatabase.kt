package com.wassha.schoolmanagement.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wassha.schoolmanagement.data.local.dao.ClassDao
import com.wassha.schoolmanagement.data.local.dao.StudentDao
import com.wassha.schoolmanagement.data.local.dao.StudentReportDao
import com.wassha.schoolmanagement.data.local.dao.TeacherDao
import com.wassha.schoolmanagement.data.local.dao.TeacherReportDao
import com.wassha.schoolmanagement.data.local.entity.ClassEntity
import com.wassha.schoolmanagement.data.local.entity.StudentClassCrossRef
import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import com.wassha.schoolmanagement.data.local.entity.StudentReportEntity
import com.wassha.schoolmanagement.data.local.entity.TeacherEntity
import com.wassha.schoolmanagement.data.local.entity.TeacherReportEntity

@Database(
    entities = [StudentEntity::class, TeacherEntity::class,
        ClassEntity::class, StudentClassCrossRef::class, StudentReportEntity::class, TeacherReportEntity::class],
    version = 4,
    exportSchema = false
)
abstract class SchoolDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun teacherDao(): TeacherDao
    abstract fun classDao(): ClassDao
    abstract fun studentReportDao(): StudentReportDao
    abstract fun teacherReportDao() : TeacherReportDao

    companion object {
        @Volatile
        private var INSTANCE: SchoolDatabase? = null

        fun getDatabase(context: Context): SchoolDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SchoolDatabase::class.java,
                    "school_database"
                )
                    .fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
