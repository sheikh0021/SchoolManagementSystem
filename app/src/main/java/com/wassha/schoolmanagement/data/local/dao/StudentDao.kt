package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

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

    // new queries
    //get all students in the school
    @Query("SELECT * FROM students ORDER BY className ASC , section ASC, rollNumber ASC")
    fun getAllStudents() : Flow<List<StudentEntity>>

    //get student by specific class
    @Query("SELECT * FROM students WHERE className = :className ORDER BY section ASC, rollNumber ASC")
    fun getStudentsByClass(className: String): Flow<List<StudentEntity>>

    //get students by class and section
    @Query("SELECT * FROM students WHERE className = :className AND section = :section ORDER BY rollNumber ASC")
    fun getStudentsByClassAndSection(className: String, section: String) : Flow<List<StudentEntity>>

    //search student by name
    @Query("SELECT * FROM students WHERE fullName LIKE '%' || :query || '%' ORDER BY fullName ASC")
    fun searchStudentsByName(query: String): Flow<List<StudentEntity>>

    //get total count of the students
    @Query("SELECT COUNT(*) FROM students")
    suspend fun getStudentCount() : Int

    //get count of students
    @Query("SELECT COUNT(*) FROM students WHERE className = :className")
    suspend fun getStudentCountByClass(className: String): Int
}
