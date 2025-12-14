package com.wassha.schoolmanagement.domain.model

import java.io.Serializable


data class Student(

    val studentId: String,
    val fullName: String,
    val className: String,
    val section: String,
    val rollNumber: String
) : Serializable
