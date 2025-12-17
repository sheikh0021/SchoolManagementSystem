package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.wassha.schoolmanagement.data.local.entity.ClassEntity
import com.wassha.schoolmanagement.data.local.entity.ClassWithTeacher
import com.wassha.schoolmanagement.data.local.entity.StudentClassCrossRef
import kotlinx.coroutines.flow.Flow


@Dao
interface ClassDao {
    //insertion operations
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClass(classEntity: ClassEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertClasses(classes: List<ClassEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enrollStudent(studentClassCrossRef: StudentClassCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enrollStudents(enrollments: List<StudentClassCrossRef>)

    //Query operations
    @Query("SELECT * FROM classes WHERE teacherId = :teacherId")
    fun getClassesByTeacher(teacherId: String): Flow<List<ClassEntity>>

    @Query("SELECT * FROM classes WHERE dayOfWeek = :day")
    fun getClassesByDay(day: String): Flow<List<ClassEntity>>

    @Transaction
    @Query("SELECT * FROM classes WHERE classId= :classId")
    fun getClassesWithTeacher(classId: Long): Flow<ClassWithTeacher?>

    //get all classes for a specific student
    @Transaction
    @Query("""
        SELECT * FROM classes INNER JOIN student_classes ON classes.classId = student_classes.classId
        WHERE student_classes.studentId = :studentId
    """)
    fun getClassesForStudent(studentId: String): Flow<List<ClassEntity>>

    //get student's today's classes
    @Transaction
    @Query("""
        SELECT * FROM classes INNER JOIN student_classes ON classes.classId = student_classes.classId
        WHERE student_classes.studentId = :studentId AND classes.dayOfWeek = :dayOfWeek
        ORDER BY classes.startTime
    """)
    fun getStudentClassesByDay(studentId: String, dayOfWeek: String): Flow<List<ClassEntity>>

    //get teacher's today's classes
    @Query("""
        SELECT * FROM classes WHERE teacherId = :teacherId AND dayOfWeek = :dayOfWeek
        ORDER BY startTime
    """)
    fun getTeacherClassesByDay(teacherId: String, dayOfWeek: String): Flow<List<ClassEntity>>

    //delete operations
    @Delete
    suspend fun deleteClass(classEntity: ClassEntity)

    @Query("DELETE FROM student_classes WHERE studentId = :studentId AND classId = :classId")
    suspend fun unenrollStudent(studentId: String, classId: Long)
}