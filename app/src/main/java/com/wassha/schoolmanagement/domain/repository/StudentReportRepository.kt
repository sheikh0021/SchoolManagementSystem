package com.wassha.schoolmanagement.domain.repository

import com.wassha.schoolmanagement.data.local.dao.StudentReportDao
import com.wassha.schoolmanagement.data.local.entity.StudentReportEntity
import kotlinx.coroutines.flow.Flow

class StudentReportRepository(private val reportDao: StudentReportDao) {
    fun getReportsForStudent(studentId: String): Flow<List<StudentReportEntity>>{
        return reportDao.getReportsForStudent(studentId)
    }

    fun getCurrentTermReport(studentId: String, term: String): Flow<StudentReportEntity?> {
        return reportDao.getCurrentTermReport(studentId, term)
    }

    suspend fun insertReport(report: StudentReportEntity): Long {
        return reportDao.insertReport(report)
    }

    suspend fun updateReport(report: StudentReportEntity){
        reportDao.updateReport(report)
    }
}