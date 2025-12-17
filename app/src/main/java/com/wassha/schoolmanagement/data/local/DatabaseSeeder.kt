package com.wassha.schoolmanagement.data.local

import android.content.Context
import android.content.SharedPreferences
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.data.local.entity.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

/**
 * Master Database Seeder
 * Seeds all initial data: Classes, Reports for both Students and Teachers
 */
class DatabaseSeeder(
    private val database: SchoolDatabase,
    private val context: Context
) {
    
    private val prefs: SharedPreferences = 
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_SEEDED = "database_seeded_v1"
        private const val CURRENT_TERM = "Fall 2024"
    }
    
    /**
     * Main seeding function - checks if already seeded
     */
    fun seedDatabaseIfNeeded() {
        val isAlreadySeeded = prefs.getBoolean(KEY_SEEDED, false)
        
        if (!isAlreadySeeded) {
            seedAllData()
        }
    }
    
    /**
     * Seeds all data for all users
     */
    private fun seedAllData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Seed for each student in the database
                // In a real app, you'd query existing students
                // For now, using example IDs
                
                val studentIds = listOf("S001", "S002", "S003")
                val teacherIds = listOf("T001", "T002")
                
                studentIds.forEach { studentId ->
                    seedStudentData(studentId)
                }
                
                teacherIds.forEach { teacherId ->
                    seedTeacherData(teacherId)
                }
                
                // Mark as seeded
                prefs.edit().putBoolean(KEY_SEEDED, true).apply()
                
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    
    /**
     * Seed all data for a specific student
     */
    private suspend fun seedStudentData(studentId: String) {
        seedStudentClasses(studentId)
        seedStudentReport(studentId)
    }
    
    /**
     * Seed all data for a specific teacher
     */
    private suspend fun seedTeacherData(teacherId: String) {
        seedTeacherClasses(teacherId)
        seedTeacherReport(teacherId)
    }
    
    // ==================== STUDENT CLASSES ====================
    
    private suspend fun seedStudentClasses(studentId: String) {
        val teacherId = "T001"
        val teacherName = "Mr. Kavi"
        
        // Create classes for multiple days
        val classes = mutableListOf<ClassEntity>()
        
        // Monday classes
        classes.add(ClassEntity(
            className = "Mathematics",
            section = "A",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Monday"
        ))
        
        classes.add(ClassEntity(
            className = "Science",
            section = "A",
            teacherId = "T002",
            teacherName = "Mr. Iqbal",
            startTime = "10:00",
            endTime = "10:50",
            dayOfWeek = "Monday"
        ))
        
        classes.add(ClassEntity(
            className = "Urdu",
            section = "A",
            teacherId = "T003",
            teacherName = "Mr. Hamid",
            startTime = "12:00",
            endTime = "12:50",
            dayOfWeek = "Monday"
        ))
        
        // Tuesday classes
        classes.add(ClassEntity(
            className = "Social Science",
            section = "A",
            teacherId = "T004",
            teacherName = "Mr. Mustajaab",
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Tuesday"
        ))
        
        classes.add(ClassEntity(
            className = "Computer",
            section = "A",
            teacherId = "T005",
            teacherName = "Mr. Faiz",
            startTime = "10:00",
            endTime = "10:50",
            dayOfWeek = "Tuesday"
        ))
        
        // Wednesday classes
        classes.add(ClassEntity(
            className = "English",
            section = "A",
            teacherId = "T006",
            teacherName = "Ms. Sarah",
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Wednesday"
        ))
        
        classes.add(ClassEntity(
            className = "Physics",
            section = "A",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "11:00",
            endTime = "11:50",
            dayOfWeek = "Wednesday"
        ))
        
        // Insert classes and enroll student
        classes.forEach { classEntity ->
            val classId = database.classDao().insertClass(classEntity)
            database.classDao().enrollStudent(
                StudentClassCrossRef(studentId, classId)
            )
        }
    }
    
    // ==================== STUDENT REPORTS ====================
    
    private suspend fun seedStudentReport(studentId: String) {
        val report = StudentReportEntity(
            reportId = 0,
            studentId = studentId,
            attendance = 92,
            grades = "A",
            performance = "Excellent",
            engagement = "High",
            attendanceDescription = "Excellent attendance this term. You've been present for 92% of all scheduled classes. Keep up the great work!",
            gradesDescription = "Consistent academic performance across all subjects with an A grade average. Your dedication to studies is commendable.",
            performanceDescription = "Strong overall improvement with excellent participation in class activities. Your problem-solving skills have notably improved.",
            engagementDescription = "Active class participation with high engagement in discussions and group projects. You're a role model for other students.",
            term = CURRENT_TERM,
            lastUpdated = System.currentTimeMillis()
        )
        
        database.studentReportDao().insertReport(report)
    }
    
    // ==================== TEACHER CLASSES ====================
    
    private suspend fun seedTeacherClasses(teacherId: String) {
        val teacherName = "Mr. Kavi"  // In real app, fetch from database
        
        val classes = mutableListOf<ClassEntity>()
        
        // Get current day for realistic data
        val today = LocalDate.now()
            .dayOfWeek
            .getDisplayName(TextStyle.FULL, Locale.ENGLISH)
        
        // Monday classes
        classes.add(ClassEntity(
            className = "Class 10",
            section = "A",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Monday"
        ))
        
        classes.add(ClassEntity(
            className = "Class 9",
            section = "B",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "10:00",
            endTime = "10:50",
            dayOfWeek = "Monday"
        ))
        
        classes.add(ClassEntity(
            className = "Class 11",
            section = "A",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "11:00",
            endTime = "11:50",
            dayOfWeek = "Monday"
        ))
        
        // Tuesday classes
        classes.add(ClassEntity(
            className = "Class 10",
            section = "B",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Tuesday"
        ))
        
        classes.add(ClassEntity(
            className = "Class 12",
            section = "A",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "14:00",
            endTime = "14:50",
            dayOfWeek = "Tuesday"
        ))
        
        // Wednesday classes
        classes.add(ClassEntity(
            className = "Class 9",
            section = "C",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Wednesday"
        ))
        
        classes.add(ClassEntity(
            className = "Class 11",
            section = "B",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "11:00",
            endTime = "11:50",
            dayOfWeek = "Wednesday"
        ))
        
        // Thursday classes
        classes.add(ClassEntity(
            className = "Class 10",
            section = "C",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "10:00",
            endTime = "10:50",
            dayOfWeek = "Thursday"
        ))
        
        // Friday classes
        classes.add(ClassEntity(
            className = "Class 12",
            section = "B",
            teacherId = teacherId,
            teacherName = teacherName,
            startTime = "09:00",
            endTime = "09:50",
            dayOfWeek = "Friday"
        ))
        
        database.classDao().insertClasses(classes)
    }
    
    // ==================== TEACHER REPORTS ====================
    
    private suspend fun seedTeacherReport(teacherId: String) {
        val report = TeacherReportEntity(
            reportId = 0,
            teacherId = teacherId,
            attendance = 95,
            performance = "Excellent",
            rating = 4.8,
            classCompletion = 98,
            attendanceDescription = "Excellent attendance record this term. You've maintained a consistent presence in all scheduled classes and have only missed 5% of classes due to approved leave.",
            performanceDescription = "Outstanding teaching performance with strong student engagement and effective lesson delivery. Your innovative teaching methods have received positive feedback from both students and administration.",
            ratingDescription = "Highly rated by students with an average score of 4.8 out of 5.0. Your teaching methods and student support are greatly appreciated. Students particularly appreciate your clear explanations and approachability.",
            classCompletionDescription = "Nearly all scheduled classes (98%) have been successfully completed with quality instruction. Your punctuality and preparation are exemplary.",
            term = CURRENT_TERM,
            lastUpdated = System.currentTimeMillis()
        )
        
        database.teacherReportDao().insertReport(report)
    }
    
    /**
     * Force re-seed (useful for testing)
     * Call this to clear the seeded flag and re-seed data
     */
    fun forceReseed() {
        prefs.edit().putBoolean(KEY_SEEDED, false).apply()
        seedAllData()
    }
    
    /**
     * Check if database has been seeded
     */
    fun isSeeded(): Boolean {
        return prefs.getBoolean(KEY_SEEDED, false)
    }
}