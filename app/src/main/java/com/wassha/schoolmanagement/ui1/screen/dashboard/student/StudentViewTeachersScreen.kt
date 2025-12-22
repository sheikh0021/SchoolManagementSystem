package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import android.app.Application
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wassha.schoolmanagement.data.local.entity.TeacherWithSubject
import com.wassha.schoolmanagement.domain.model.Student
import com.wassha.schoolmanagement.ui1.viewmodel.StudentViewTeachersViewModel


//colors and theme
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

//screen view teachers screen
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentViewTeachersScreen(
    navController: NavController,
    student: Student? = null,
    viewModel: StudentViewTeachersViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            LocalContext.current.applicationContext as Application
        )
    )
){
    val myTeachers by viewModel.myTeachers.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    //load teachers when screen opens
    LaunchedEffect(student?.studentId) {
        student?.studentId?.let { studentId ->
            viewModel.loadMyTeachers(studentId)
        }
    }

    StudentDashboardScaffold(
        navController = navController,
        currentRoute = "student_view_teachers",
        student = student,
        topBarTitle = { Text("My Teachers") }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().background(ScreenBg).padding(innerPadding)
        ) {
            //header card
            Card(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth().background(OrangeGradient).padding(20.dp)
                ){
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "My Teachers",
                                style = MaterialTheme.typography.titleLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Fall 2025",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.School,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            //Teacher list
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (myTeachers.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PersonOff,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = Color.Gray
                        )
                        Text(
                            text = "No Teacher assigned yet",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(myTeachers) {  teacher ->
                        TeacherCard(
                            teacher = teacher,
                            onClick = {
                                //todo navigate to teachers details and contacts
                            }
                        )

                    }
                }
            }
        }
    }
}

//teacher card component
@Composable
private fun TeacherCard(
    teacher: TeacherWithSubject,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth().clickable{onClick()}.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp),
            ambientColor = GlowOrange,
            spotColor = GlowOrange
        ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = SoftCardColor)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                //Avatar
                Box(
                    modifier = Modifier.size(48.dp).background(
                        GlowOrange.copy(alpha = 0.15f),
                        CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = null,
                        tint = GlowOrange,
                        modifier = Modifier.size(28.dp)
                    )
                }
                //Teacher Info
                Column {
                    Text(
                        text = teacher.teacherName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = teacher.subject,
                        style = MaterialTheme.typography.bodyMedium,
                        color = GlowOrange,
                        fontWeight = FontWeight.Medium
                    )
                    if (teacher.phoneNumber.isNotEmpty()) {
                        Text(
                            text = teacher.phoneNumber,
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                }
            }
            //call icon
            Icon(
                imageVector = Icons.Default.Phone,
                contentDescription = "Contact",
                tint = GlowOrange
            )
        }
    }
}