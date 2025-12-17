package com.wassha.schoolmanagement.ui1.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class TeacherBottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
){
    object Profile :  TeacherBottomNavItem(
        route = "teacher_dashboard",
        label = "Profile",
        icon = Icons.Default.Person
    )
    object Classes :  TeacherBottomNavItem(
        route = "teacher_classes",
        label = "Classes",
        icon = Icons.Default.Groups
    )
    object Reports :  TeacherBottomNavItem(
        route = "teacher_reports",
        label = "Reports",
        icon = Icons.Default.Assessment
    )
    object Settings :  TeacherBottomNavItem(
        route = "teacher_settings",
        label = "Settings",
        icon = Icons.Default.Settings
    )
}


