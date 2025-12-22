package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "teacher_student_assignments",
    foreignKeys = [
        ForeignKey(
            entity = TeacherEntity::class,
            parentColumns = ["teacherId"],
            childColumns = ["teacherId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("teacherId"), Index("studentId")]
)
data class TeacherStudentAssignmentEntity(
    @PrimaryKey(autoGenerate = true)
    val assignmentId: Long = 0,

    val teacherId: String, //which teacher
    val studentId: String, //which student
    val subject: String,  //what subject
    val term : String,
    val year: Int,
    val isActive: Boolean, //currently teaching
    val createdAt: Long
)