package com.wassha.schoolmanagement.ui1.screen.dashboard.teacher

import android.app.Application
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wassha.schoolmanagement.data.local.entity.ClassEntity
import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.domain.model.TeacherClass
import com.wassha.schoolmanagement.ui1.viewmodel.TeacherClassesViewModel

/* ---------------------------------------------------
   🎨 DESIGN CONSTANTS (Same theme as dashboard)
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
private val SoftCardColor = Color(0xFFF1F2F6)

/* ---------------------------------------------------
   👩‍🏫 TEACHER CLASSES SCREEN
--------------------------------------------------- */

@Composable
fun TeacherClassesScreen(
    navController: NavController,
    teacher: Teacher? = null,
    viewModel: TeacherClassesViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            LocalContext.current.applicationContext as Application
        )
    )
) {

    //collect state with viewmodel
    val todayClasses by viewModel.todayClasses.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    //load the data when screen opens up
    LaunchedEffect(teacher?.teacherId) {
        teacher?.teacherId?.let { teacherId ->
            viewModel.loadTodayClasses(teacherId)
        }
    }

    // 🔹 Holds the selected class for dialog
    var selectedClass by remember { mutableStateOf<ClassEntity?>(null) }

    TeacherDashboardScaffold(
        navController = navController,
        currentRoute = "teacher_classes",
        teacher = teacher,
        topBarTitle = { Text("Today's Classes") }
    ) { innerPadding ->

        if (isLoading){
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(color = GlowPurple)
            }
        } else if (todayClasses.isEmpty()){

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ){
             Text(text = "No classes scheduled for today",
                 style = MaterialTheme.typography.bodyLarge,
                 color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(todayClasses) { cls ->
                    TeacherClassCard(
                        classEntity = cls,
                        onClick = { selectedClass = cls }
                        )
                }
            }
        }
    }

    // 🔹 Show dialog when class is selected
    selectedClass?.let {
        TeacherClassDetailDialog(
            classEntity = it,
            onDismiss = { selectedClass = null }
        )
    }
}

/* ---------------------------------------------------
   📚 CLICKABLE CLASS CARD
--------------------------------------------------- */

@Composable
private fun TeacherClassCard(
    classEntity: ClassEntity,
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column {
                Text(
                    text = classEntity.className,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "Section: ${classEntity.section}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Schedule,
                    contentDescription = null,
                    tint = GlowPurple
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "${classEntity.startTime} - ${classEntity.endTime}",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

/* ---------------------------------------------------
   💬 BEAUTIFUL CUSTOM DIALOG
--------------------------------------------------- */

@Composable
private fun TeacherClassDetailDialog(
    classEntity: ClassEntity,
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
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                Text(
                    text = classEntity.className,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )

                Text(
                    text = "Section: ${classEntity.section}",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White.copy(alpha = 0.9f)
                )

                Text(
                    text = "Time: ${classEntity.startTime} - ${classEntity.endTime}",
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
                    Text("Close", color = GlowPurple)
                }
            }
        }
    }
}
