package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassha.schoolmanagement.data.local.entity.TeacherEntity

@Dao
interface TeacherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeacher(teacher: TeacherEntity)

    @Query("SELECT * FROM teachers WHERE teacherId = :id AND password = :password")
    suspend fun loginTeacher(
        id: String,
        password: String
    ): TeacherEntity?   // ✅ MUST be TeacherEntity
}
