package com.wassha.schoolmanagement.domain.repository

import com.wassha.schoolmanagement.data.local.dao.StudentDao
import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import kotlinx.coroutines.flow.Flow

class StudentRepository(private val studentDao: StudentDao) {

    //get all students in the school
    fun getAllStudents() : Flow<List<StudentEntity>>{
        return studentDao.getAllStudents()
    }

    //get student by filter by class
    fun getStudentsByClass(className: String): Flow<List<StudentEntity>>{
        return studentDao.getStudentsByClass(className)
    }

    //get students filtered by class and section
    fun getStudentsByClassAndSection(
        className: String,
        section: String
    ) : Flow<List<StudentEntity>> {
        return studentDao.getStudentsByClassAndSection(className, section)
    }

    //search students by name
    fun searchStudents(query: String) : Flow<List<StudentEntity>> {
        return studentDao.searchStudentsByName(query)
    }

    //get total number of students
    suspend fun getTotalStudentCount(): Int {
        return studentDao.getStudentCount()
    }

    //get total number of counts
    suspend fun getStudentCountByClass(className: String) : Int {
        return studentDao.getStudentCountByClass(className)
    }

}