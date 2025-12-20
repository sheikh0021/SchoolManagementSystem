package com.wassha.schoolmanagement.ui1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import com.wassha.schoolmanagement.domain.repository.StudentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TeacherViewStudentsViewModel(application: Application) : AndroidViewModel(application) {

    //repository instance
    private val repository : StudentRepository

    init {
        val database = SchoolDatabase.getDatabase(application)
        repository = StudentRepository(database.studentDao())
    }

    //ui state

    //list of students to display
    private val _students = MutableStateFlow<List<StudentEntity>>(emptyList())
    val students: StateFlow<List<StudentEntity>> = _students.asStateFlow()

    //loading indicator
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    //selected filter - class number
    private val _selectedClass = MutableStateFlow<String?>(null)
    val selectedClass: StateFlow<String?> = _selectedClass.asStateFlow()

    //selected filter - section filter
    private val _selectedSection = MutableStateFlow<String?>(null)
    val selectedSection: StateFlow<String?> = _selectedSection.asStateFlow()

    //search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    //total student count
    private val _totalCount = MutableStateFlow(0)
    val totalCount: StateFlow<Int> = _totalCount.asStateFlow()

    //functions
    //load all students
    fun loadAllStudents(){
        viewModelScope.launch {
            _isLoading.value = true
            repository.getAllStudents().collect { studentList ->
                _students.value = studentList
                _isLoading.value = false
            }
        }

        //also load the total count
        viewModelScope.launch {
            _totalCount.value = repository.getTotalStudentCount()
        }
    }

    //filter students by class
    fun filterByClass(className: String?) {
        _selectedClass.value = className
        _selectedSection.value = null // reset the section when class changes

        viewModelScope.launch {
            _isLoading.value = true
            if (className == null){
                //show all students
                repository.getAllStudents().collect { studentList ->
                    _students.value = studentList
                    _isLoading.value = false
                }
            }else {
                //show only selected class students
                repository.getStudentsByClass(className).collect { studentList ->
                    _students.value = studentList
                    _isLoading.value = false
                }
            }
        }
    }

    //filter by class and section
    fun filterByClassAndSection(className: String, section: String){
        _selectedClass.value = className
        _selectedSection.value = section

        viewModelScope.launch {
            _isLoading.value = true
            repository.getStudentsByClassAndSection(className, section).collect { studentList ->
                _students.value = studentList
                _isLoading.value = false
            }
        }
    }

    //search students by name
    fun searchStudents(query: String) {
        _searchQuery.value = query

        if (query.isEmpty()){
            //if search cleared , reload all
            loadAllStudents()
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            repository.searchStudents(query).collect { studentList ->
                _students.value = studentList
                _isLoading.value = false
            }
        }
    }

    //clear all filters
    fun clearFilters(){
        _selectedClass.value = null
        _selectedSection.value = null
        _searchQuery.value = ""
        loadAllStudents()
    }

}