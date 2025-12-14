package com.wassha.schoolmanagement.domain.use_case.auth

import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.domain.repository.AuthRepository

class RegisterTeacherUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(teacher: Teacher, password: String) {
        repository.registerTeacher(teacher, password)
    }
}