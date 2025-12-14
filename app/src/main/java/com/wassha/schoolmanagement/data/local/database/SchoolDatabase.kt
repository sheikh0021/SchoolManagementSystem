package com.wassha.schoolmanagement.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wassha.schoolmanagement.data.local.dao.StudentDao
import com.wassha.schoolmanagement.data.local.dao.TeacherDao
import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import com.wassha.schoolmanagement.data.local.entity.TeacherEntity

@Database(
    entities = [StudentEntity::class, TeacherEntity::class],
    version = 2
)
abstract class SchoolDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao
    abstract fun teacherDao(): TeacherDao
}
