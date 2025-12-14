package com.wassha.schoolmanagement.domain.model

import java.io.Serializable

data class Teacher(

    val teacherId: String,
    val fullName: String,
    val subject: String,
    val phoneNumber: String
) : Serializable
