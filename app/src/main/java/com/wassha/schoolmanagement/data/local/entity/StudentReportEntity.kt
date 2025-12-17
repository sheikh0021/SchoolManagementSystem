package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "student_reports",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE)
    ],
    indices = [Index("studentId")]
)
data class StudentReportEntity(
    @PrimaryKey(autoGenerate = true)
    val reportId: Long = 0,

    val studentId: String,
    val attendance: Int,
    val grades: String,
    val performance: String,
    val engagement: String,
    val attendanceDescription: String,      // ADD THIS
    val gradesDescription: String,          // ADD THIS
    val performanceDescription: String,     // ADD THIS
    val engagementDescription: String,      // ADD THIS
    val term: String,
    val lastUpdated: Long
)