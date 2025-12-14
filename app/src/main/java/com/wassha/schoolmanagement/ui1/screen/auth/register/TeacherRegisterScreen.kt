package com.wassha.schoolmanagement.ui1.screen.auth.register

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
fun TeacherRegisterScreen(
    navController: NavController
) {
    // ✅ get Application once
    val application = LocalContext.current.applicationContext as Application

    // ✅ use getInstance(application)
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(application)
    )

    var teacherId by remember { mutableStateOf("") }
    var fullName by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Teacher Registration", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = teacherId,
            onValueChange = { teacherId = it },
            label = { Text("Teacher ID") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = subject,
            onValueChange = { subject = it },
            label = { Text("Subject") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        Button(
            onClick = {
                authViewModel.registerTeacher(
                    teacherId = teacherId,
                    fullName = fullName,
                    subject = subject,
                    phoneNumber = phoneNumber,
                    password = password
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Register")
        }

        Spacer(Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Already have an account? ",
                style = MaterialTheme.typography.bodyMedium
            )
            TextButton(
                onClick = {
                    navController.navigate(Routes.TEACHER_LOGIN) {
                        popUpTo(Routes.TEACHER_REGISTER) { inclusive = true }
                    }
                }
            ) {
                Text("Login")
            }
        }

        Spacer(Modifier.height(16.dp))

        when (authState) {
            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthUiState.TeacherRegistered -> {
                LaunchedEffect(Unit) {
                    // After successful registration → go back to Teacher Login
                    navController.navigate(Routes.TEACHER_LOGIN) {
                        popUpTo(Routes.TEACHER_REGISTER) { inclusive = true }
                    }
                    authViewModel.resetState()
                }
            }

            is AuthUiState.Error -> {
                val message = (authState as AuthUiState.Error).message
                Text(text = message, color = MaterialTheme.colorScheme.error)
            }

            else -> {}
        }
    }
}

