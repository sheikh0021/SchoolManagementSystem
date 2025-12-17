package com.wassha.schoolmanagement.ui1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.ClassEntity
import com.wassha.schoolmanagement.domain.repository.ClassRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherClassesViewModel(application: Application) : AndroidViewModel(application) {
    
    private val repository: ClassRepository
    
    init {
        val database = SchoolDatabase.getDatabase(application)
        repository = ClassRepository(database.classDao())
    }
    
    private val _todayClasses = MutableStateFlow<List<ClassEntity>>(emptyList())
    val todayClasses: StateFlow<List<ClassEntity>> = _todayClasses.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadTodayClasses(teacherId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            repository.getTodayClassesForTeacher(teacherId).collect { classes ->
                _todayClasses.value = classes
                _isLoading.value = false
            }
        }
    }
}
