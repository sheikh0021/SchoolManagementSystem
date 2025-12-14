package com.wassha.schoolmanagement.ui1.viewmodel

import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.model.Teacher

sealed class AuthUiState {

    object Idle : AuthUiState()

    object Loading : AuthUiState()

    data class StudentSuccess(
        val student: Student
    ) : AuthUiState()

    data class TeacherSuccess(
        val teacher: Teacher
    ) : AuthUiState()

    // ✅ Registration Success (NO student object needed yet)
    object StudentRegistered : AuthUiState()

    object TeacherRegistered : AuthUiState()

    data class Error(
        val message: String
    ) : AuthUiState()

    data class Message(
        val message: String
    ) : AuthUiState()
}
