package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "teacher_reports",
    foreignKeys = [
        ForeignKey(
            entity = TeacherEntity::class,
            parentColumns = ["teacherId"],
            childColumns = ["teacherId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("teacherId")]
)
data class TeacherReportEntity(
    @PrimaryKey(autoGenerate = true)
    val reportId: Long = 0,

    val teacherId: String,
    val attendance: Int,
    val performance: String,
    val rating: Double,
    val attendanceDescription: String,
    val performanceDescription: String,
    val ratingDescription: String,
    val classCompletionDescription: String,
    val classCompletion: Int,
    val term : String,
    val lastUpdated: Long
)