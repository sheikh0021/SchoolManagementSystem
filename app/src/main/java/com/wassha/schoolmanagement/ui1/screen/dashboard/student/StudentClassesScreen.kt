package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.domain.model.StudentClass

/* ---------------------------------------------------
   🎨 DESIGN CONSTANTS (Same theme as dashboard)
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

/* ---------------------------------------------------
   🧑‍🎓 STUDENT CLASSES SCREEN
--------------------------------------------------- */

@Composable
fun StudentClassesScreen(
    navController: NavController,
    student: Student? = null
) {

    // 🔹 Holds the selected class for dialog
    var selectedClass by remember { mutableStateOf<StudentClass?>(null) }

    val todayClasses = listOf(
        StudentClass("Mathematics", "Mr Kavi", "09:00 - 09:50"),
        StudentClass("Science", "Mr Iqbal", "10:00 - 10:50"),
        StudentClass("Urdu", "Mr Hamid", "12:00 - 12:50"),
        StudentClass("Social Science", "Mr Mustajaab", "01:00 - 02:50"),
        StudentClass("Computer", "Mr Faiz", "03:00 - 04:50")
    )

    StudentDashboardScaffold(
        navController = navController,
        currentRoute = "student_classes",
        student = student,
        topBarTitle = { Text("Today's Classes") }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(todayClasses) { cls ->
                ClassCard(
                    studentClass = cls,
                    onClick = { selectedClass = cls }
                )
            }
        }
    }

    // 🔹 Show dialog when class is selected
    selectedClass?.let {
        ClassDetailDialog(
            studentClass = it,
            onDismiss = { selectedClass = null }
        )
    }
}

/* ---------------------------------------------------
   📚 CLICKABLE CLASS CARD
--------------------------------------------------- */

@Composable
private fun ClassCard(
    studentClass: StudentClass,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .shadow(
                elevation = 12.dp,
                shape = RoundedCornerShape(20.dp),
                ambientColor = GlowOrange,
                spotColor = GlowOrange
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = studentClass.subject,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "Teacher: ${studentClass.teacher}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = GlowOrange
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(studentClass.time)
            }
        }
    }
}

/* ---------------------------------------------------
   💬 BEAUTIFUL CUSTOM DIALOG (NO AlertDialog ❌)
--------------------------------------------------- */

@Composable
private fun ClassDetailDialog(
    studentClass: StudentClass,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = OrangeGradient,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = studentClass.subject,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

                Text(
                    text = "Teacher: ${studentClass.teacher}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Text(
                    text = "Time: ${studentClass.time}",
                    style = MaterialTheme.typography.titleMedium,
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
                    Text("Close", color = GlowOrange)
                }
            }
        }
    }
}
