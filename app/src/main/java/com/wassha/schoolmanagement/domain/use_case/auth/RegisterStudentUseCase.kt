package com.wassha.schoolmanagement.domain.use_case.auth

import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.repository.AuthRepository

class RegisterStudentUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        student: Student,
        password: String
    ) {
        repository.registerStudent(student, password)
    }
}