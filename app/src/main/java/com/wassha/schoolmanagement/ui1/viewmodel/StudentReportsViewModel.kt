package com.wassha.schoolmanagement.ui1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.StudentReportEntity
import com.wassha.schoolmanagement.domain.repository.StudentReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StudentReportsViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: StudentReportRepository
    
    init {
        val database = SchoolDatabase.getDatabase(application)
        repository = StudentReportRepository(database.studentReportDao())
    }

    private val _currentReport = MutableStateFlow<StudentReportEntity?>(null)
    val currentReport: StateFlow<StudentReportEntity?> = _currentReport.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadCurrentTermReport(studentId: String, term: String = "Fall 2024") {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getCurrentTermReport(studentId, term).collect { report ->
                _currentReport.value = report
                _isLoading.value = false
            }
        }
    }
}
