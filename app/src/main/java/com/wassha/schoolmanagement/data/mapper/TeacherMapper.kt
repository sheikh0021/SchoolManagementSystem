package com.wassha.schoolmanagement.data.mapper

import com.wassha.schoolmanagement.data.local.entity.TeacherEntity
import com.wassha.schoolmanagement.domain.model.Teacher

// ✅ Room → Domain (login flow)
fun TeacherEntity.toDomain(): Teacher {
    return Teacher(
        teacherId = teacherId,
        fullName = fullName,
        subject = subject,
        phoneNumber = phoneNumber
    )
}

// ✅ Domain → Room (registration flow)
fun Teacher.toEntity(password: String): TeacherEntity {
    return TeacherEntity(
        teacherId = teacherId,
        fullName = fullName,
        subject = subject,
        phoneNumber = phoneNumber,
        password = password
    )
}
