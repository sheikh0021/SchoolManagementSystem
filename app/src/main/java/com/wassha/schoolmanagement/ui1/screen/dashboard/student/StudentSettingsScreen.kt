package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wassha.schoolmanagement.domain.model.Student

/* ---------------------------------------------------
   🎨 COLORS & GRADIENT
--------------------------------------------------- */

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
   ⚙️ STUDENT SETTINGS SCREEN
--------------------------------------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentSettingsScreen(
    navController: NavController,
    student: Student
) {
    StudentDashboardScaffold(
        navController = navController,
        currentRoute = "student_settings",
        topBarTitle = { Text("Settings") }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(ScreenBg)
                .padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            /* ---------------- PROFILE HEADER ---------------- */

            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    shape = RoundedCornerShape(26.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent),
                    elevation = CardDefaults.cardElevation(0.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(OrangeGradient)
                            .shadow(
                                elevation = 30.dp,
                                shape = RoundedCornerShape(26.dp),
                                ambientColor = GlowOrange,
                                spotColor = GlowOrange
                            )
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            // Avatar
                            Box(
                                modifier = Modifier
                                    .size(72.dp)
                                    .background(
                                        Color.White.copy(alpha = 0.2f),
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = "Profile",
                                    tint = Color.White,
                                    modifier = Modifier.size(40.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = student.fullName,
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White
                            )

                            Text(
                                text = "Class ${student.className} - ${student.section}",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                    }
                }
            }

            /* ---------------- PROFILE DETAILS ---------------- */

            item { ProfileInfoCard("Roll Number", student.rollNumber) }
            item { ProfileInfoCard("Student ID", student.studentId) }
            item { ProfileInfoCard("Class", student.className) }
            item { ProfileInfoCard("Section", student.section) }

            /* ---------------- SECURITY ---------------- */

            item {
                ChangePasswordCard()
            }
        }
    }
}

/* ---------------------------------------------------
   🔹 PROFILE INFO CARD
--------------------------------------------------- */

@Composable
private fun ProfileInfoCard(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardColor),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(18.dp)
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

/* ---------------------------------------------------
   🔐 CHANGE PASSWORD CARD
--------------------------------------------------- */

@Composable
private fun ChangePasswordCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardColor),
        elevation = CardDefaults.cardElevation(10.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Change Password",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Update your account security",
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(
                        GlowOrange.copy(alpha = 0.15f),
                        RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Change Password",
                    tint = GlowOrange
                )
            }
        }
    }
}
