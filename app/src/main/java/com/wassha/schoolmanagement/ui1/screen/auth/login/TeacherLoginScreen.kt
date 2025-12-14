package com.wassha.schoolmanagement.ui1.screen.auth.login

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.wassha.schoolmanagement.ui1.navigation.Routes
import com.wassha.schoolmanagement.ui1.viewmodel.AuthUiState
import com.wassha.schoolmanagement.ui1.viewmodel.AuthViewModel
import com.wassha.schoolmanagement.ui1.viewmodel.AuthViewModelFactory

@Composable
fun TeacherLoginScreen(
    navController: NavController
) {
    // ✅ CORRECT way to get Application inside Compose
    val application = LocalContext.current.applicationContext as Application

    // ✅ CORRECT way to create ViewModel with your factory
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(application)
    )

    var teacherId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Teacher Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = teacherId,
            onValueChange = { teacherId = it },
            label = { Text("Teacher ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                authViewModel.loginTeacher(teacherId, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(
                onClick = {
                    navController.navigate(Routes.TEACHER_REGISTER)
                }
            ) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when (authState) {

            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthUiState.TeacherSuccess -> {
                val teacher = (authState as AuthUiState.TeacherSuccess).teacher
                LaunchedEffect(teacher) {
                    navController.currentBackStackEntry?.savedStateHandle?.set("teacher", teacher)

                    navController.navigate(Routes.TEACHER_DASHBOARD)
                    authViewModel.resetState()
                }
            }

            is AuthUiState.Error -> {
                val message = (authState as AuthUiState.Error).message
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }

            else -> Unit
        }
    }
}
