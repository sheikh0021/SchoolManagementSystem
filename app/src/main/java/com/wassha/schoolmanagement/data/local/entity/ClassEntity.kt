package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
tableName = "classes",
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
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    val classId: Long = 0,
    val className:  String,
    val section: String,
    val teacherId: String,
    val teacherName: String,
    val startTime: String,
    val endTime: String,
    val dayOfWeek: String
)