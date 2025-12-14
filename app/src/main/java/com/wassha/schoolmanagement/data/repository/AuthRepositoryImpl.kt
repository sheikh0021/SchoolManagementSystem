package com.wassha.schoolmanagement.data.repository

import com.wassha.schoolmanagement.data.local.dao.StudentDao
import com.wassha.schoolmanagement.data.local.dao.TeacherDao
import com.wassha.schoolmanagement.data.mapper.toDomain
import com.wassha.schoolmanagement.data.mapper.toEntity
import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val studentDao: StudentDao,
    private val teacherDao: TeacherDao
) : AuthRepository {

    // ✅ STUDENT LOGIN
    override suspend fun loginStudent(
        rollNumber: String,
        password: String,
        className: String,
        section: String
    ): Student? {

        android.util.Log.d("STUDENT_LOGIN_QUERY", """
            SEARCHING FOR:
            Class = $className
            Section = $section
            Roll = $rollNumber
            Password = $password
        """.trimIndent())

        val entity = studentDao.loginStudent(
            className = className,
            section = section,
            rollNumber = rollNumber,
            password = password
        )

        if (entity != null) {
            android.util.Log.d("STUDENT_LOGIN_SUCCESS", "Found student: ${entity.fullName}")
        } else {
            android.util.Log.d("STUDENT_LOGIN_FAILED", "No student found with these credentials")
        }

        return entity?.toDomain()
    }

    // ✅ TEACHER LOGIN
    override suspend fun loginTeacher(
        teacherId: String,
        password: String
    ): Teacher? {

        val entity = teacherDao.loginTeacher(
            id = teacherId,
            password = password
        )

        return entity?.toDomain()
    }

    override suspend fun registerStudent(student: Student, password: String) {

        val entity = student.toEntity(password)

        android.util.Log.d("STUDENT_REGISTER", """
        INSERTING:
        Name = ${entity.fullName}
        Class = ${entity.className}
        Section = ${entity.section}
        Roll = ${entity.rollNumber}
        Password = ${entity.password}
    """.trimIndent())

        studentDao.insertStudent(entity)
    }

    // ✅ TEACHER REGISTRATION
    override suspend fun registerTeacher(
        teacher: Teacher,
        password: String
    ) {
        val entity = teacher.toEntity(password)
        teacherDao.insertTeacher(entity)
    }
}
