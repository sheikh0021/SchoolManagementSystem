package com.wassha.schoolmanagement.domain.use_case.auth

import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.repository.AuthRepository

class LoginStudentUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        className: String,
        section: String,
        rollNumber: String,
        password: String
    ): Student? {
        return repository.loginStudent(rollNumber, password, className, section)
    }
}