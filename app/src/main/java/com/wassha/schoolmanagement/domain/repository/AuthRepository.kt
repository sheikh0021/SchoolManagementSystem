package com.wassha.schoolmanagement.domain.repository

import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.model.Teacher

interface AuthRepository {

    // STUDENT
    suspend fun loginStudent(
        rollNumber: String,
        password: String,
        className: String,
        section: String
    ): Student?

    suspend fun registerStudent(
        student: Student,
        password: String
    )

    // TEACHER
    suspend fun loginTeacher(
        teacherId: String,
        password: String
    ): Teacher?

    suspend fun registerTeacher(
        teacher: Teacher,
        password: String
    )
}
