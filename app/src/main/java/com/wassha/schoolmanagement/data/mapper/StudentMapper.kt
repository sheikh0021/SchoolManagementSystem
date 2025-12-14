package com.wassha.schoolmanagement.data.mapper

import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import com.wassha.schoolmanagement.domain.model.Student

// ✅ Room → Domain (login flow)
fun StudentEntity.toDomain(): Student {
    return Student(
        studentId = rollNumber,
        fullName = fullName,
        className = className,
        section = section,
        rollNumber = rollNumber
    )
}

// ✅ Domain → Room (registration flow) ✅ PASSWORD IS PASSED HERE
fun Student.toEntity(password: String): StudentEntity {
    return StudentEntity(
        studentId = studentId,
        fullName = fullName,
        className = className,
        section = section,
        rollNumber = rollNumber,
        password = password
    )
}
