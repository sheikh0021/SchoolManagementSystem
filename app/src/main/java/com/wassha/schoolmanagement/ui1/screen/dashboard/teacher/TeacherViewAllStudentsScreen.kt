package com.wassha.schoolmanagement.ui1.screen.dashboard.teacher

import android.app.Application
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
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOff
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wassha.schoolmanagement.data.local.entity.StudentEntity
import com.wassha.schoolmanagement.domain.model.Teacher
import com.wassha.schoolmanagement.ui1.viewmodel.TeacherViewStudentsViewModel


private val TeacherGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF6A11CB),
        Color(0xFF8E2DE2),
        Color(0xFFB388FF)

    ),
    start = Offset(0f, 0f),
    end = Offset(1000f, 600f)
)

private val GlowPurple = Color(0xFF8E2DE2)
private val SoftCardColor = Color(0xFFF1F2F6)
private val ScreenBg = Color(0xFFF8F9FD)

//teacher view all students screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherViewAllStudentsScreen(
    navController: NavController,
    teacher: Teacher? = null,
    viewModel: TeacherViewStudentsViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory.getInstance(
            LocalContext.current.applicationContext as Application
        )
    )
){
    //collect state from viewmodel
    val students by viewModel.students.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val selectedClass by viewModel.selectedClass.collectAsState()
    val totalCount by viewModel.totalCount.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    //load all students when screen opens
    LaunchedEffect(Unit) {
        viewModel.loadAllStudents()
    }

        //state for filter dropdown
    var showFilterMenu by remember { mutableStateOf(false) }

    TeacherDashboardScaffold(
        navController = navController,
        currentRoute = "teacher_view_students",
        teacher = teacher,
        topBarTitle = { Text("All Students") }
    ) {innerPadding ->
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
                    modifier = Modifier.fillMaxWidth().background(TeacherGradient).padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Total Students",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.White.copy(alpha = 0.9f)
                            )
                            Text(text = "$totalCount",
                                style = MaterialTheme.typography.headlineLarge,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
            //search and filter bar
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                //search bar
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {viewModel.searchStudents(it)},
                    modifier = Modifier.weight(1f),
                    placeholder = {Text("Search by Name of the student")},
                    leadingIcon = {Icon(Icons.Default.Search, null)
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()){
                            IconButton(onClick = {viewModel.searchStudents("") }) {
                                Icon(Icons.Default.Clear, "Clear")
                            }
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true
                )
                //filter button
                Box {
                    FilledTonalButton(
                        onClick = {showFilterMenu = true},
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = GlowPurple.copy(alpha = 0.15f)
                        )
                    ) {
                        Icon(Icons.Default.FilterList, "Filter")
                    }
                    //filter dropdown menu
                    DropdownMenu(
                        expanded = showFilterMenu,
                        onDismissRequest = {showFilterMenu = false}
                    ) {
                        DropdownMenuItem(
                            text = {Text("All Classes")},
                            onClick = {
                                viewModel.clearFilters()
                                showFilterMenu = false
                            }
                        )
                        Divider()
                        //class 1st to 12th
                        (1..12).forEach { classNum ->
                            DropdownMenuItem(
                                text = {Text("Class $classNum")},
                                onClick = {
                                    viewModel.filterByClass(classNum.toString())
                                    showFilterMenu = false
                                }
                            )

                        }
                    }
                }
            }

            //filter chips
            if (selectedClass != null) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = true,
                        onClick = {viewModel.clearFilters()},
                        label = {Text("Class $selectedClass")},
                        trailingIcon = {
                            Icon(
                                Icons.Default.Close,
                                "Remove",
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            //student list
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = GlowPurple)
                }
            } else if (students.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
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
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(students) { student ->
                        StudentCard(
                            student = student,
                            onClick= {

                            }
                        )

                    }
                }
            }

        }

    }

}

//student card components
@Composable
private fun StudentCard(
    student : StudentEntity,
    onClick: () -> Unit
){
    Card(
        modifier = Modifier.fillMaxWidth().clickable{onClick()}.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(16.dp),
            ambientColor = GlowPurple,
            spotColor = GlowPurple
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
                //avatar
                Box(
                    modifier = Modifier.size(48.dp).background(GlowPurple.copy(alpha = 0.15f),
                        CircleShape
                    ),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = student.fullName.first().toString(),
                        style = MaterialTheme.typography.titleLarge,
                        color = GlowPurple,
                        fontWeight = FontWeight.Bold
                    )
                }
                //student info
                Column {
                    Text(
                        text = student.fullName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Class ${student.className} - ${student.section} . Roll ${student.rollNumber}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }
            //arrow icon
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "View Details",
                tint = GlowPurple
            )
        }
    }
}