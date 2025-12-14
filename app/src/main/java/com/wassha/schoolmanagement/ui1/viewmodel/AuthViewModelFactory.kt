package com.wassha.schoolmanagement.ui1.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.wassha.schoolmanagement.SchoolApplication
import com.wassha.schoolmanagement.domain.use_case.auth.LoginStudentUseCase
import com.wassha.schoolmanagement.domain.use_case.auth.LoginTeacherUseCase
import com.wassha.schoolmanagement.domain.use_case.auth.RegisterStudentUseCase
import com.wassha.schoolmanagement.domain.use_case.auth.RegisterTeacherUseCase

class AuthViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {

            val app = application as SchoolApplication
            val authRepository = app.authRepo

            val loginStudentUseCase = LoginStudentUseCase(authRepository)
            val loginTeacherUseCase = LoginTeacherUseCase(authRepository)
            val registerStudentUseCase = RegisterStudentUseCase(authRepository)
            val registerTeacherUseCase = RegisterTeacherUseCase(authRepository)

            return AuthViewModel(
                loginStudentUseCase = loginStudentUseCase,
                loginTeacherUseCase = loginTeacherUseCase,
                registerStudentUseCase = registerStudentUseCase,
                registerTeacherUseCase = registerTeacherUseCase
            ) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }

    companion object {

        // ✅ THIS MUST RETURN AuthViewModelFactory — NOT Unit
        fun getInstance(application: Application): AuthViewModelFactory {
            return AuthViewModelFactory(application)
        }
    }
}
