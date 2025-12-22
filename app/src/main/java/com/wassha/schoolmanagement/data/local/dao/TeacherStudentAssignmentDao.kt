package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wassha.schoolmanagement.data.local.entity.TeacherStudentAssignmentEntity
import com.wassha.schoolmanagement.data.local.entity.TeacherWithSubject
import kotlinx.coroutines.flow.Flow


@Dao
interface TeacherStudentAssignmentDao{

    //assign a teacher to a student for a subject
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignment(assignment: TeacherStudentAssignmentEntity) : Long

    //assign multiple teachers at once
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAssignments(assignments: List<TeacherStudentAssignmentEntity>)

    //get all teachers for a specific student
    @Query("""
        SELECT 
        t.teacherId,
        t.fullName as teacherName,
        tsa.subject,
        t.phoneNumber
    FROM teacher_student_assignments tsa
    INNER JOIN teachers t ON tsa.teacherId = t.teacherId
    WHERE tsa.studentId = :studentId
    AND tsa.isActive = 1
    AND tsa.term = :term
    ORDER BY tsa.subject ASC
    """)
    fun getTeachersForStudent(studentId: String, term: String): Flow<List<TeacherWithSubject>>

    //get all students taught by teacher in current term
    @Query("""
        SELECT studentId 
        FROM teacher_student_assignments
        WHERE teacherId = :teacherId
        AND isActive = 1
        AND term = :term
    """)
    fun getStudentsForTeacher(teacherId: String, term: String): Flow<List<String>>

    //check if a teacher teaches a specific student
    @Query("""
        SELECT COUNT(*) > 0
        FROM teacher_student_assignments
        WHERE teacherId = :teacherId
        AND studentId = :studentId
        AND isActive = 1
    """)
    suspend fun isTeacherAssignedToStudent(teacherId: String, studentId: String): Boolean

    //deactivate assignments when term ends
    @Query("""
        UPDATE teacher_student_assignments
        SET isActive = 0
        WHERE assignmentId= :assignmentId
    """)
    suspend fun deactivateAssignment(assignmentId: Long)

    //deactivate all assignments for a term
    @Query("""
        UPDATE teacher_student_assignments
        SET isActive = 0 
        WHERE term = :term
    """)
    suspend fun deactivateAssignmentsForTerm(term: String)
}