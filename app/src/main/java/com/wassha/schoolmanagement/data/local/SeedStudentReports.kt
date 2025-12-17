package com.wassha.schoolmanagement.data.local

import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.StudentReportEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


object SeedStudentReports {
    fun seedReports(database: SchoolDatabase, studentId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val report = StudentReportEntity(
                reportId = 0,
                studentId = studentId,
                attendance = 92,
                grades = "A",
                performance = "Excellent",
                engagement = "High",
                attendanceDescription = "Excellent attendance this term. You'have been present for 92% of all classes",
                gradesDescription = "Consistent academic performance across all subjects with an A grade",
                performanceDescription = "Strong overall improvement with excellent participation in class activities",
                engagementDescription = "Active class participation with high engagement",
                term = "Fall 2025",
                lastUpdated = System.currentTimeMillis()
            )
            database.studentReportDao().insertReport(report)
        }
    }
}