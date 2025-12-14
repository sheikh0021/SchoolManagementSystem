package com.wassha.schoolmanagement.ui1.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.domain.use_case.auth.LoginStudentUseCase
import com.wassha.schoolmanagement.domain.use_case.auth.LoginTeacherUseCase
import com.wassha.schoolmanagement.domain.use_case.auth.RegisterStudentUseCase
import com.wassha.schoolmanagement.domain.use_case.auth.RegisterTeacherUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginStudentUseCase: LoginStudentUseCase,
    private val loginTeacherUseCase: LoginTeacherUseCase,
    private val registerStudentUseCase: RegisterStudentUseCase,
    private val registerTeacherUseCase: RegisterTeacherUseCase
) : ViewModel() {

    // --------------------------------------------
    // 🔵 UI STATE (What the UI Observes)
    // --------------------------------------------

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState

    // --------------------------------------------
    // 🔵 STUDENT LOGIN
    // --------------------------------------------

    fun loginStudent(
        className: String,
        section: String,
        rollNumber: String,
        password: String
    ) {
        viewModelScope.launch {

            android.util.Log.d("STUDENT_LOGIN", """
            TRY LOGIN:
            Class = $className
            Section = $section
            Roll = $rollNumber
            Password = $password
        """.trimIndent())

            _authState.value = AuthUiState.Loading

            val student = loginStudentUseCase(
                className,
                section,
                rollNumber,
                password
            )

            if (student != null) {
                _authState.value = AuthUiState.StudentSuccess(student)
            } else {
                _authState.value = AuthUiState.Error("Invalid student credentials")
            }
        }
    }

    // --------------------------------------------
    // 🟣 TEACHER LOGIN
    // --------------------------------------------

    fun loginTeacher(teacherId: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading

            val teacher = loginTeacherUseCase(teacherId, password)

            if (teacher != null) {
                _authState.value = AuthUiState.TeacherSuccess(teacher)
            } else {
                _authState.value = AuthUiState.Error("Invalid teacher credentials")
            }
        }
    }

    // --------------------------------------------
// 🟢 STUDENT REGISTRATION ✅ FIXED
// --------------------------------------------

    fun registerStudent(
        fullName: String,
        className: String,
        section: String,
        rollNumber: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading

            try {
                // Create Student object - using rollNumber as studentId
                val student = Student(
                    studentId = rollNumber,
                    fullName = fullName,
                    className = className,
                    section = section,
                    rollNumber = rollNumber
                )

                registerStudentUseCase(student, password)

                // ✅ Proper success signal
                _authState.value = AuthUiState.StudentRegistered

            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(
                    e.message ?: "Student registration failed"
                )
            }
        }
    }

// --------------------------------------------
// 🟠 TEACHER REGISTRATION ✅ FIXED
// --------------------------------------------

    fun registerTeacher(
        teacherId: String,
        fullName: String,
        subject: String,
        phoneNumber: String,
        password: String
    ) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading

            try {
                // Create Teacher object
                val teacher = Teacher(
                    teacherId = teacherId,
                    fullName = fullName,
                    subject = subject,
                    phoneNumber = phoneNumber
                )

                registerTeacherUseCase(teacher, password)

                // ✅ Proper success signal
                _authState.value = AuthUiState.TeacherRegistered

            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(
                    e.message ?: "Teacher registration failed"
                )
            }
        }
    }

    // --------------------------------------------
    // 🔁 Reset State (After Navigation or Toast)
    // --------------------------------------------

    fun resetState() {
        _authState.value = AuthUiState.Idle
    }
}
