package com.wassha.schoolmanagement.ui1.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.ui1.screen.auth.login.RoleSelectionScreen
import com.wassha.schoolmanagement.ui1.screen.auth.login.StudentLoginScreen
import com.wassha.schoolmanagement.ui1.screen.auth.login.TeacherLoginScreen
import com.wassha.schoolmanagement.ui1.screen.auth.register.StudentRegisterScreen
import com.wassha.schoolmanagement.ui1.screen.auth.register.TeacherRegisterScreen
import com.wassha.schoolmanagement.ui1.screen.dashboard.student.StudentClassesScreen
import com.wassha.schoolmanagement.ui1.screen.dashboard.student.StudentDashboard
import com.wassha.schoolmanagement.ui1.screen.dashboard.student.StudentReportsScreen
import com.wassha.schoolmanagement.ui1.screen.dashboard.student.StudentSettingsScreen
import com.wassha.schoolmanagement.ui1.screen.dashboard.teacher.TeacherClassesScreen
import com.wassha.schoolmanagement.ui1.screen.dashboard.teacher.TeacherDashboard
import com.wassha.schoolmanagement.ui1.screen.dashboard.teacher.TeacherReportsScreen
import com.wassha.schoolmanagement.ui1.screen.dashboard.teacher.TeacherSettingsScreen
import com.wassha.schoolmanagement.ui1.screen.splash.SplashScreen

object Routes {
    const val SPLASH = "splash"
    const val ROLE_SELECTION = "role_selection"

    const val STUDENT_LOGIN = "student_login"
    const val STUDENT_REGISTER = "student_register"
    const val STUDENT_DASHBOARD = "student_dashboard"

    const val TEACHER_LOGIN = "teacher_login"
    const val TEACHER_REGISTER = "teacher_register"
    const val TEACHER_DASHBOARD = "teacher_dashboard"
}

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Routes.SPLASH
    ) {

        composable(Routes.SPLASH) {
            SplashScreen(navController)
        }

        composable(Routes.ROLE_SELECTION) {
            RoleSelectionScreen(navController)
        }

        // ---------------- STUDENT ----------------

        composable(Routes.STUDENT_LOGIN) {
            StudentLoginScreen(navController)
        }

        composable(Routes.STUDENT_REGISTER) {
            StudentRegisterScreen(navController)
        }

 composable(Routes.STUDENT_DASHBOARD) { backStackEntry : NavBackStackEntry ->

     val student = navController.previousBackStackEntry?.savedStateHandle?.get<com.wassha.schoolmanagement.domain.model.Student>("student")

     if (student != null){
         StudentDashboard(student = student, navController = navController)
     }else {
         Text(
             text = "No Student data available",
             modifier = Modifier.padding(24.dp)
         )
     }

 }
        // ---------------- TEACHER ----------------

        composable(Routes.TEACHER_LOGIN) {
            TeacherLoginScreen(navController)
        }

        composable(Routes.TEACHER_REGISTER) {
            TeacherRegisterScreen(navController)
        }

        composable(Routes.TEACHER_DASHBOARD) { backStackEntry ->
            val teacher = navController.previousBackStackEntry?.savedStateHandle?.get<com.wassha.schoolmanagement.domain.model.Teacher>("teacher")

            if(teacher != null){
                TeacherDashboard(teacher = teacher, navController = navController)
            }else {
                Text(text = "No teacher data available",
                    modifier = Modifier.padding(24.dp))

            }
        }

        composable("student_classes") { backStackEntry ->
            val student = navController.previousBackStackEntry?.savedStateHandle?.get<Student>("student")
            StudentClassesScreen(navController, student)
        }

        composable("student_reports") { backStackEntry ->
            val student = navController.previousBackStackEntry?.savedStateHandle?.get<Student>("student")
            StudentReportsScreen(navController, student)
        }

        composable("student_settings") { backStackEntry ->
            val student = navController.previousBackStackEntry?.savedStateHandle?.get<Student>("student")
            if (student != null) {
                StudentSettingsScreen(navController, student)
            } else {
                Text(
                    text = "No Student data available",
                    modifier = Modifier.padding(24.dp)
                )
            }
        }

        composable("teacher_classes") { backStackEntry ->
            val teacher = navController.previousBackStackEntry?.savedStateHandle?.get<Teacher>("teacher")
            TeacherClassesScreen(navController, teacher)
        }

        composable("teacher_reports") { backStackEntry ->
            val teacher = navController.previousBackStackEntry?.savedStateHandle?.get<Teacher>("teacher")
            TeacherReportsScreen(navController, teacher)
        }

        composable("teacher_settings") { backStackEntry ->
            val teacher = navController.previousBackStackEntry?.savedStateHandle?.get<Teacher>("teacher")
            TeacherSettingsScreen(navController, teacher)
        }

    }
}


