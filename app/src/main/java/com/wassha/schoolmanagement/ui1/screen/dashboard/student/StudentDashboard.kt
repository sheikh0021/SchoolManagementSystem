package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.wassha.schoolmanagement.domain.model.Student


//--Dashboard --//

private val OrangeGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFFFF8A00),
        Color(0xFFFFA726),
        Color(0xFFFFCC80)
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 600f)
)

private val GlowOrange = Color(0xFFFFA726)
private val SoftCardColor = Color(0xFFF1F2F6)
private val ScreenBg = Color(0xFFF8F9FD)

/* ---------------------------------------------------
   🧑‍🎓 STUDENT DASHBOARD
--------------------------------------------------- */

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard(student: Student) {

    Scaffold(
        containerColor = ScreenBg,

        /* ---------------- TOP BAR ---------------- */

        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Welcome Back 👋🏻",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.Gray
                        )
                        Text(
                            text = student.fullName,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notifications"
                        )
                    }
                }
            )
        },

        /* ---------------- BOTTOM BAR ---------------- */

        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFF5F6FA)
            ) {
                NavigationBarItem(
                    selected = true,
                    onClick = {},
                    icon = { Icon(Icons.Default.Person, null) },
                    label = { Text("Profile") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = GlowOrange,
                        selectedTextColor = GlowOrange,
                        indicatorColor = GlowOrange.copy(alpha = 0.15f)
                    )
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Class, null) },
                    label = { Text("Classes") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Assessment, null) },
                    label = { Text("Reports") }
                )
                NavigationBarItem(
                    selected = false,
                    onClick = {},
                    icon = { Icon(Icons.Default.Settings, null) },
                    label = { Text("Settings") }
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            /* ---------------- OVERVIEW CARD ---------------- */

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(OrangeGradient)
                        .shadow(
                            elevation = 30.dp,
                            shape = RoundedCornerShape(24.dp),
                            ambientColor = GlowOrange,
                            spotColor = GlowOrange
                        )
                        .padding(24.dp)
                ) {
                    Column {
                        Text(
                            text = "Student Overview",
                            color = Color.White.copy(alpha = 0.9f),
                            style = MaterialTheme.typography.labelLarge
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "${student.className} - ${student.section}",
                            color = Color.White,
                            style = MaterialTheme.typography.headlineMedium
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Roll No: ${student.rollNumber}",
                            color = Color.White.copy(alpha = 0.95f),
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            /* ---------------- INFO GRID ---------------- */

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardInfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Student ID",
                    value = student.studentId,
                    icon = Icons.Default.Badge
                )
                DashboardInfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Class",
                    value = student.className,
                    icon = Icons.Default.School
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DashboardInfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Section",
                    value = student.section,
                    icon = Icons.Default.Groups
                )
                DashboardInfoCard(
                    modifier = Modifier.weight(1f),
                    title = "Roll No",
                    value = student.rollNumber,
                    icon = Icons.Default.ConfirmationNumber
                )
            }
        }
    }
}

/* ---------------------------------------------------
   🔁 REUSABLE INFO CARD
--------------------------------------------------- */

@Composable
private fun DashboardInfoCard(
    modifier: Modifier = Modifier,
    title: String,
    value: String,
    icon: ImageVector
) {
    Card(
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(18.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Box(
                modifier = Modifier
                    .size(38.dp)
                    .background(
                        color = GlowOrange.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = GlowOrange
                )
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
