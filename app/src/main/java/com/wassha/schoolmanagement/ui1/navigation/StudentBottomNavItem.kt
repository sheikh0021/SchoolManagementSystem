package com.wassha.schoolmanagement.ui1.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Class
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class StudentBottomNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector
){
    object Profile :  StudentBottomNavItem(
        route = "student_dashboard",
        label = "Profile",
        icon = Icons.Default.Person
    )
    object Classes :  StudentBottomNavItem(
        route = "student_classes",
        label = "Classes",
        icon = Icons.Default.Class
    )
    object Reports :  StudentBottomNavItem(
        route = "student_reports",
        label = "Reports",
        icon = Icons.Default.Assessment
    )
    object Settings :  StudentBottomNavItem(
        route = "student_settings",
        label = "Settings",
        icon = Icons.Default.Settings
    )

}