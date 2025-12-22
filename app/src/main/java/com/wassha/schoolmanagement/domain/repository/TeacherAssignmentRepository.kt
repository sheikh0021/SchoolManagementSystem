package com.wassha.schoolmanagement.domain.repository



import com.wassha.schoolmanagement.data.local.dao.TeacherStudentAssignmentDao
import com.wassha.schoolmanagement.data.local.entity.TeacherStudentAssignmentEntity
import com.wassha.schoolmanagement.data.local.entity.TeacherWithSubject
import kotlinx.coroutines.flow.Flow

class TeacherAssignmentRepository(
    private val assignmentDao: TeacherStudentAssignmentDao
) {
    fun getMyTeachers(studentId: String, term: String = "Fall 2025"): Flow<List<TeacherWithSubject>> {
        return assignmentDao.getTeachersForStudent(studentId, term)
    }

    //get all students
    fun getMyStudents(teacherId: String, term: String = "Fall 2025"): Flow<List<String>>{
        return assignmentDao.getStudentsForTeacher(teacherId, term)
    }

    suspend fun createAssignment(assignment: TeacherStudentAssignmentEntity): Long {
        return assignmentDao.insertAssignment(assignment)
    }

    suspend fun isMyTeacher(teacherId: String, studentId: String): Boolean{
        return assignmentDao.isTeacherAssignedToStudent(teacherId, studentId)
    }
}