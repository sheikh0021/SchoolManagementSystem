package com.wassha.schoolmanagement.domain.use_case.auth

import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.domain.repository.AuthRepository

class LoginTeacherUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        teacherId: String,
        password: String
    ): Teacher? {
        return repository.loginTeacher(teacherId, password)
    }
}