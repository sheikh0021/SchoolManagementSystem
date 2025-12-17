package com.wassha.schoolmanagement.domain.repository

import com.wassha.schoolmanagement.data.local.dao.ClassDao
import com.wassha.schoolmanagement.data.local.entity.ClassEntity
import com.wassha.schoolmanagement.data.local.entity.StudentClassCrossRef
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale


class ClassRepository(private val classDao: ClassDao) {

    fun getClassesForStudent(studentId: String): Flow<List<ClassEntity>>{
        return classDao.getClassesForStudent(studentId)
    }
    fun getTodayClassesForStudent(studentId: String): Flow<List<ClassEntity>> {
        val today = getCurrentDayOfWeek()
        return classDao.getStudentClassesByDay(studentId,today)
    }

    fun getClassesByTeacher(teacherId: String): Flow<List<ClassEntity>>{
        return classDao.getClassesByTeacher(teacherId)
    }

    fun getTodayClassesForTeacher(teacherId: String): Flow<List<ClassEntity>>{
        val today  =getCurrentDayOfWeek()
        return classDao.getTeacherClassesByDay(teacherId, today)
    }

    suspend fun insertClass(classEntity: ClassEntity): Long {
        return classDao.insertClass(classEntity)
    }

    suspend fun enrollStudent(studentId: String, classId: Long){
        classDao.enrollStudent(StudentClassCrossRef(studentId, classId))
    }

    private fun getCurrentDayOfWeek(): String {
        return LocalDate.now().dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH )
    }

}