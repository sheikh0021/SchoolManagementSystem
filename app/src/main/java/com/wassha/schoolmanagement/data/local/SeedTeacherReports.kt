package com.wassha.schoolmanagement.data.local

import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.TeacherReportEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object SeedTeacherReports {

    fun seedReports(database: SchoolDatabase, teacherId: String){
        CoroutineScope(Dispatchers.IO).launch {
            val report = TeacherReportEntity(
                reportId = 0,
                teacherId = teacherId,
                attendance = 95,
                performance = "Excellent",
                rating = 4.8,
                classCompletion = 98,
                attendanceDescription = "Excellent attendance record this term",
                performanceDescription = "Outstanding teaching performance with strong student engagement and effective lesson delivery",
                ratingDescription = "Highly rated by students. Your teaching methods and student support are greatly appreciated",
                classCompletionDescription = "Nearly all scheduled classes have been successfully completed with quality instructions",
                term = "Fall 2025",
                lastUpdated = System.currentTimeMillis()
            )
            database.teacherReportDao().insertReport(report)
        }
    }

}