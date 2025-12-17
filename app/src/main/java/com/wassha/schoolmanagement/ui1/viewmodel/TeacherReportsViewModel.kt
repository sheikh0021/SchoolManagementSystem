package com.wassha.schoolmanagement.ui1.viewmodel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.TeacherReportEntity
import com.wassha.schoolmanagement.domain.repository.TeacherReportRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherReportsViewModel( application : Application ): AndroidViewModel(application){
    private val repository: TeacherReportRepository

    init {
        val database = SchoolDatabase.getDatabase(application)
        repository = TeacherReportRepository(database.teacherReportDao())
    }
    private val _currentReport = MutableStateFlow<TeacherReportEntity?>(null)
    val currentReport: StateFlow<TeacherReportEntity?> = _currentReport.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadCurrentTermReport(teacherId: String, term: String = "Fall 2025"){
        viewModelScope.launch {
            _isLoading.value = true
            repository.getCurrentTermReport(teacherId, term).collect { report ->
                _currentReport.value = report
                _isLoading.value = false
            }
        }
    }

}
