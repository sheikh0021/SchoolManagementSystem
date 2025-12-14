package com.wassha.schoolmanagement.ui1.screen.auth.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wassha.schoolmanagement.ui1.navigation.Routes

@Composable
fun RoleSelectionScreen(
    navController: NavController
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // ✅ Styled Heading
        Text(
            text = "Select Your Role",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "Please choose how you want to continue",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(48.dp))

        // ✅ Student Button
        RoleButton(
            text = "Student Login",
            icon = Icons.Default.School
        ) {
            navController.navigate(Routes.STUDENT_LOGIN)
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ Teacher Button
        RoleButton(
            text = "Teacher Login",
            icon = Icons.Default.Person
        ) {
            navController.navigate(Routes.TEACHER_LOGIN)
        }
    }
}

@Composable
private fun RoleButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
