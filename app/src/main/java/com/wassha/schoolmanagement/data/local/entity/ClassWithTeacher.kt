package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation


data class ClassWithTeacher(
@Embedded val classEntity: ClassEntity,
    @Relation(
        parentColumn = "teacherId",
        entityColumn = "teacherId"
    )
    val teacher: TeacherEntity
)