package com.wassha.schoolmanagement.ui1.screen.dashboard.student

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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

/* ---------------------------------------------------
   🎨 COLORS & GRADIENTS
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
   🧑‍🎓 STUDENT DASHBOARD CONTENT
--------------------------------------------------- */

@Composable
fun StudentDashboardContent(student: Student, scaffoldPadding: PaddingValues) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 16.dp
        )
    ) {

        /* ---------------- OVERVIEW CARD ---------------- */

        item {
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
                    androidx.compose.foundation.layout.Column {
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
        }

        /* ---------------- INFO GRID ROW 1 ---------------- */

        item {
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
        }

        /* ---------------- INFO GRID ROW 2 ---------------- */

        item {
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
        androidx.compose.foundation.layout.Column(
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

            androidx.compose.foundation.layout.Column {
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
