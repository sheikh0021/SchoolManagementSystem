package com.wassha.schoolmanagement.domain.repository

import com.wassha.schoolmanagement.data.local.dao.TeacherReportDao
import com.wassha.schoolmanagement.data.local.entity.TeacherReportEntity
import kotlinx.coroutines.flow.Flow


class TeacherReportRepository(private val reportDao: TeacherReportDao) {
    fun getReportsForTeacher(teacherId: String): Flow<List<TeacherReportEntity>>{
          return reportDao.getReportsForTeacher(teacherId)
    }
    fun getCurrentTermReport(teacherId: String, term: String): Flow<TeacherReportEntity?>{
        return reportDao.getCurrentTermReport(teacherId, term)
    }

    suspend fun insertReport(report: TeacherReportEntity): Long {
        return reportDao.insertReport(report)
    }
    suspend fun updateReport(report: TeacherReportEntity){
        reportDao.updateReport(report)
    }
}