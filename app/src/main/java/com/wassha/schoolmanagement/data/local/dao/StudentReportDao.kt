package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wassha.schoolmanagement.data.local.entity.StudentReportEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface StudentReportDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: StudentReportEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<StudentReportEntity>)

    @Update
    suspend fun updateReport(report: StudentReportEntity)

    @Query("SELECT * FROM student_reports WHERE studentId = :studentId ORDER BY lastUpdated DESC")
    fun getReportsForStudent(studentId: String): Flow<List<StudentReportEntity>>

    @Query("SELECT * FROM student_reports WHERE studentId = :studentId AND term = :term LIMIT 1")
    fun getCurrentTermReport(studentId: String, term: String): Flow<StudentReportEntity?>

    @Query("SELECT * FROM student_reports WHERE reportId = :reportId")
    suspend fun getReportById(reportId: Long): StudentReportEntity?

    @Delete
    suspend fun deleteReport(report: StudentReportEntity)
}