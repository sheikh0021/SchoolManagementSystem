package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassha.schoolmanagement.data.local.entity.StudentEntity

@Dao
interface StudentDao {

    // ✅ REGISTER
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: StudentEntity)

    // ✅ LOGIN (CLASS + SECTION + ROLL + PASSWORD)
    // Room automatically converts camelCase property names to snake_case in SQL
    @Query("""
        SELECT * FROM students 
        WHERE className = :className 
        AND section = :section 
        AND rollNumber = :rollNumber 
        AND password = :password
    """)
    suspend fun loginStudent(
        className: String,
        section: String,
        rollNumber: String,
        password: String
    ): StudentEntity?
}
