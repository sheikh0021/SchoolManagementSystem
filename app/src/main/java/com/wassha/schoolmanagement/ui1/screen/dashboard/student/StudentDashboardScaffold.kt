package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.wassha.schoolmanagement.ui1.navigation.StudentBottomNavItem


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboardScaffold(
    navController: NavController,
    currentRoute: String,
    student: com.wassha.schoolmanagement.domain.model.Student? = null,
    topBarTitle: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    val items = listOf(
        StudentBottomNavItem.Profile,
        StudentBottomNavItem.Classes,
        StudentBottomNavItem.Reports,
        StudentBottomNavItem.Settings
    )

    Scaffold(
        topBar = {
            TopAppBar(title = {topBarTitle()})
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFF5F6FA)) {
                items.forEach {
                    item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route){
                                // Pass student data to the next screen
                                student?.let {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("student", it)
                                }
                                navController.navigate(item.route){
                                    popUpTo("student_dashboard") {inclusive = false}
                                    launchSingleTop =  true
                                }
                            }
                        },
                        icon = { Icon(item.icon, null) },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }

}