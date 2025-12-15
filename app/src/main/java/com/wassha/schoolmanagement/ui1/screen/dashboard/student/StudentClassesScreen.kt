package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wassha.schoolmanagement.domain.model.StudentClass


@Composable
fun StudentClassesScreen(
    navController: NavController
){
   val todayClasses = listOf(
       StudentClass("Mathematics", "Mr Kavi", "09:00 - 09:50"),
       StudentClass("Science", "Mr Iqbal", "10:00 - 10:50"),
       StudentClass("Urdu", "Mr Hamid", "12:00 - 12:50"),
       StudentClass("Social Science", "Mr Mustajaab", "01:00 - 02:50"),
       StudentClass("Computer", "Mr Faiz", "03:00 - 04:50"),

       )
    StudentDashboardScaffold(
        navController = navController,
        currentRoute = "student_classes",
        topBarTitle = {
            Text("Today's Classes")
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(todayClasses) { cls ->
                ClassCard(cls)

            }
        }


    }

}

@Composable
private fun ClassCard(studentClass: StudentClass) {
    Card(
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(studentClass.subject, style = MaterialTheme.typography.titleMedium)
                Text("Teacher : ${studentClass.teacher}", style = MaterialTheme.typography.bodyMedium)
            }

            Row {
                Icon(Icons.Default.Schedule, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text(studentClass.time)
            }



        }
    }
}