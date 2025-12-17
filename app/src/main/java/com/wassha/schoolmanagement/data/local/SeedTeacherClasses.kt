package com.wassha.schoolmanagement.data.local

import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.ClassEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SeedTeacherClasses {
    fun seedClasses(database: SchoolDatabase, teacherId: String, teacherName: String){
        CoroutineScope(Dispatchers.IO).launch {
            val classes = listOf(
                ClassEntity(
                className = "Class 10",
                section = "A",
                teacherId = teacherId,
                teacherName = teacherName,
                startTime = "09:00",
                endTime = "09:50",
                dayOfWeek = "Monday"
            ),
                ClassEntity(
                    className = "Class 9",
                    section = "B",
                    teacherId = teacherId,
                    teacherName = teacherName,
                    startTime = "10:00",
                    endTime = "10:50",
                    dayOfWeek = "Monday"
                ),
                ClassEntity(
                    className = "Class 11",
                    section = "A",
                    teacherId = teacherId,
                    teacherName = teacherName,
                    startTime = "11:00",
                    endTime = "11:50",
                    dayOfWeek = "Monday"
                ),
                ClassEntity(
                    className = "Class 10",
                    section = "B",
                    teacherId = teacherId,
                    teacherName = teacherName,
                    startTime = "14:00",
                    endTime = "14:50",
                    dayOfWeek = "Monday"
                ),
                ClassEntity(
                    className = "Class 9",
                    section = "C",
                    teacherId = teacherId,
                    teacherName = teacherName,
                    startTime = "15:00",
                    endTime = "15:50",
                    dayOfWeek = "Monday"
                )
            )
            database.classDao().insertClasses(classes)
        }
    }
}