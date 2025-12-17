package com.wassha.schoolmanagement.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.wassha.schoolmanagement.data.local.entity.TeacherReportEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface TeacherReportDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: TeacherReportEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<TeacherReportEntity>)

    @Update
    suspend fun updateReport(report: TeacherReportEntity)

    @Query("SELECT * FROM teacher_reports WHERE teacherId = :teacherId ORDER BY lastUpdated DESC")
    fun getReportsForTeacher(teacherId: String): Flow<List<TeacherReportEntity>>

    @Query("SELECT * FROM teacher_reports WHERE teacherId = :teacherId AND term = :term LIMIT 1")
    fun getCurrentTermReport(teacherId: String, term: String): Flow<TeacherReportEntity?>

    @Query("SELECT * FROM teacher_reports WHERE reportId = :reportId")
    suspend fun getReportById(reportId: Long): TeacherReportEntity?

    @Delete
    suspend fun deleteReport(report: TeacherReportEntity)
}