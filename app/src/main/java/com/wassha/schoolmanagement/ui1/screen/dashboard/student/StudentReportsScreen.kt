package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wassha.schoolmanagement.domain.model.Student


@Composable
fun StudentReportsScreen(
    navController: NavController,
    student: com.wassha.schoolmanagement.domain.model.Student? = null
){
    StudentDashboardScaffold(
        navController = navController,
        currentRoute = "student_reports",
        student = student,
        topBarTitle = { Text("Reports") }
    ) {innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ReportCard("Attendance", "92%", Icons.Default.CheckCircle)
            }
            item {
                ReportCard("Grades", "A", Icons.Default.BarChart)
            }
            item {
                ReportCard("Performance", "Excellent", Icons.Default.TrendingUp)
            }
            item {
                ReportCard("Engagement", "High", Icons.Default.Groups)
            }

        }

    }
}

@Composable
private fun ReportCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
){
    Card(
        elevation = CardDefaults.cardElevation(8.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(title, style = MaterialTheme.typography.labelMedium)
                Text(value, style = MaterialTheme.typography.titleLarge)
            }
            Icon(icon, contentDescription = null)
        }
    }
}