package com.wassha.schoolmanagement.ui1.screen.dashboard.teacher

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.wassha.schoolmanagement.ui1.navigation.TeacherBottomNavItem

private val GlowPurple = Color(0xFF8E2DE2)
private val ScreenBg = Color(0xFFF8F9FD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboardScaffold(
    navController: NavController,
    currentRoute: String,
    teacher: com.wassha.schoolmanagement.domain.model.Teacher? = null,
    topBarTitle: @Composable () -> Unit,
    content: @Composable (PaddingValues) -> Unit
){
    val items = listOf(
        TeacherBottomNavItem.Profile,
        TeacherBottomNavItem.Classes,
        TeacherBottomNavItem.Reports,
        TeacherBottomNavItem.Settings
    )

    Scaffold(
        containerColor = ScreenBg,
        topBar = {
            TopAppBar(title = {topBarTitle()})
        },
        bottomBar = {
            NavigationBar(containerColor = Color(0xFFF5F6FA)) {
                items.forEach { item ->
                    NavigationBarItem(
                        selected = currentRoute == item.route,
                        onClick = {
                            if (currentRoute != item.route){
                                // Pass teacher data to the next screen
                                teacher?.let {
                                    navController.currentBackStackEntry?.savedStateHandle?.set("teacher", it)
                                }
                                navController.navigate(item.route){
                                    popUpTo("teacher_dashboard") {inclusive = false}
                                    launchSingleTop = true
                                }
                            }
                        },
                        icon = { Icon(item.icon, null) },
                        label = { Text(item.label) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = GlowPurple,
                            selectedTextColor = GlowPurple,
                            indicatorColor = GlowPurple.copy(alpha = 0.15f)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}


