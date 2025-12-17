package com.wassha.schoolmanagement.ui1.screen.dashboard.teacher

import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.ui1.viewmodel.TeacherReportsViewModel

/* ---------------------------------------------------
   🎨 PURPLE THEME (Teacher Reports)
--------------------------------------------------- */

private val TeacherGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF6A11CB),   // Purple
        Color(0xFF8E2DE2),   // Violet
        Color(0xFFB388FF)    // Soft glow
    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 600f)
)

private val GlowPurple = Color(0xFF8E2DE2)
private val SoftCardColor = Color(0xFFF4F1F8)

/* ---------------------------------------------------
   📊 TEACHER REPORTS SCREEN
--------------------------------------------------- */

@Composable
fun TeacherReportsScreen(
    navController: NavController,
    teacher: Teacher? = null,
    viewModel: TeacherReportsViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            LocalContext.current.applicationContext as Application
        )
    )
) {

    val currentReport by viewModel.currentReport.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    //load data when screen opens
    LaunchedEffect(teacher?.teacherId) {
        teacher?.teacherId?.let { teacherId ->
            viewModel.loadCurrentTermReport(teacherId)
        }
    }

    // 👉 Dialog state
    var selectedReportItem by remember { mutableStateOf<TeacherReportItem?>(null) }

    TeacherDashboardScaffold(
        navController = navController,
        currentRoute = "teacher_reports",
        teacher = teacher,
        topBarTitle = { Text("Reports") }
    ) { innerPadding ->

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = GlowPurple)
            }
        } else {
            currentReport?.let { report ->
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    item {
                        TeacherReportCard(
                            report = TeacherReportItem(
                                "Attendance",
                                "${report.attendance}%",
                                Icons.Default.CheckCircle,
                                report.attendanceDescription
                            ),
                            onClick = {
                                selectedReportItem = TeacherReportItem(
                                    "Attendance",
                                    "${report.attendance}%",
                                    Icons.Default.CheckCircle,
                                    report.attendanceDescription
                                )
                            }
                        )
                    }
                    item {
                        TeacherReportCard(
                            report = TeacherReportItem(
                                "Performance",
                                "${report.performance}%",
                                Icons.Default.TrendingUp,
                                report.performanceDescription
                            ),
                            onClick = {
                                selectedReportItem = TeacherReportItem(
                                    "Performance",
                                    "${report.performance}%",
                                    Icons.Default.TrendingUp,
                                    report.performanceDescription
                                )
                            }
                        )
                    }
                    item {
                        TeacherReportCard(
                            report = TeacherReportItem(
                                "Class Completion",
                                "${report.classCompletion}%",
                                Icons.Default.Assessment,
                                report.classCompletionDescription
                            ),
                            onClick = {
                                selectedReportItem = TeacherReportItem(
                                    "Class Completion",
                                    "${report.classCompletion}%",
                                    Icons.Default.Assessment,
                                    report.classCompletionDescription
                                )
                            }
                        )
                    }


                }
            } ?: Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No reports available for this term",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Gray
                    )
                }
            }
        }
    }

    //show dialog when report is selected
    selectedReportItem?.let {
        TeacherReportDetailDialog(
            report = it,
            onDismiss = { selectedReportItem = null }
        )
    }
}





/* ---------------------------------------------------
   🧾 REPORT CARD
--------------------------------------------------- */

@Composable
private fun TeacherReportCard(
    report: TeacherReportItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = GlowPurple,
                spotColor = GlowPurple
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardColor)
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = report.title,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Text(
                    text = report.value,
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            Icon(
                imageVector = report.icon,
                contentDescription = null,
                tint = GlowPurple,
                modifier = Modifier.size(32.dp)
            )
        }
    }
}

/* ---------------------------------------------------
   💬 REPORT DETAIL DIALOG (CUSTOM)
--------------------------------------------------- */

@Composable
private fun TeacherReportDetailDialog(
    report: TeacherReportItem,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = TeacherGradient,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {

                Icon(
                    imageVector = report.icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )

                Text(
                    text = report.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

                Text(
                    text = report.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onDismiss,
                    modifier = Modifier.align(Alignment.End),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    )
                ) {
                    Text("Close", color = GlowPurple)
                }
            }
        }
    }
}

/* ---------------------------------------------------
   📦 DATA MODEL (UI ONLY)
--------------------------------------------------- */

private data class TeacherReportItem(
    val title: String,
    val value: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val description: String
)


