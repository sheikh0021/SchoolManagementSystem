package com.wassha.schoolmanagement.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class StudentWithClasses(
    @Embedded val student: StudentEntity,
    @Relation(
        parentColumn = "studentId",
        entityColumn = "classId",
        associateBy = Junction(StudentClassCrossRef::class)
    )
    val classes: List<ClassEntity>
)