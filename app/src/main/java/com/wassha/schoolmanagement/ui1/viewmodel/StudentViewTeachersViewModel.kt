package com.wassha.schoolmanagement.ui1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.TeacherWithSubject
import com.wassha.schoolmanagement.domain.repository.TeacherAssignmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class StudentViewTeachersViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TeacherAssignmentRepository

    init {
        val database = SchoolDatabase.getDatabase(application)
        repository = TeacherAssignmentRepository(database.teacherStudentAssignmentDao())
    }

    //list of teachers teaching this student
    private val _myTeachers = MutableStateFlow<List<TeacherWithSubject>>(emptyList())
    val myTeachers: StateFlow<List<TeacherWithSubject>> = _myTeachers.asStateFlow()

    //loading state
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    //load teachers for a student
    fun loadMyTeachers(studentId: String, term: String = "Fall 2025") {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getMyTeachers(studentId,term).collect { teachers ->
                _myTeachers.value = teachers
                _isLoading.value = false
            }
        }
    }
}