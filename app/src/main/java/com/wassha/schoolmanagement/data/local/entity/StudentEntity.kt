package com.wassha.schoolmanagement.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "students")
data class StudentEntity(

    @PrimaryKey
    @ColumnInfo(name = "studentId")
    val studentId: String,     // Unique ID for student login

    @ColumnInfo(name = "fullName")
    val fullName: String,      // Student full name

    @ColumnInfo(name = "className")
    val className: String,     // Class (e.g., 10)

    @ColumnInfo(name = "section")
    val section: String,       // Section (e.g., A)

    @ColumnInfo(name = "rollNumber")
    val rollNumber: String,    // Roll number used for login

    @ColumnInfo(name = "password")
    val password: String      // Password for login
)
