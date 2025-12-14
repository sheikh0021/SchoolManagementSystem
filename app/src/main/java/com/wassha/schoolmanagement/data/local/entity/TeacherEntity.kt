package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "teachers")
data class TeacherEntity(

    @PrimaryKey
    val teacherId: String,     // Unique ID for teacher login

    val fullName: String,      // Teacher full name

    val subject: String,       // Subject teacher teaches

    val phoneNumber: String,  // Contact number

    val password: String      // Password for login
)
