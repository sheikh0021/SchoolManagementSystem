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
fun StudentRegisterScreen(
    navController: NavController
) {
    // ✅ get Application once
    val application = LocalContext.current.applicationContext as Application

    // ✅ use getInstance(application)
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(application)
    )

    var fullName by remember { mutableStateOf("") }
    var className by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("Student Registration", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(20.dp))

        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = className,
            onValueChange = { className = it },
            label = { Text("Class") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = section,
            onValueChange = { section = it },
            label = { Text("Section") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = rollNumber,
            onValueChange = { rollNumber = it },
            label = { Text("Roll Number") },
            modifier = Modifier.fillMaxWidth()
        )

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
                authViewModel.registerStudent(
                    fullName = fullName,
                    className = className,
                    section = section,
                    rollNumber = rollNumber,
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
                    navController.navigate(Routes.STUDENT_LOGIN) {
                        popUpTo(Routes.STUDENT_REGISTER) { inclusive = true }
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

            is AuthUiState.StudentRegistered -> {
                LaunchedEffect(Unit) {
                    // After successful registration → go back to Student Login
                    navController.navigate(Routes.STUDENT_LOGIN) {
                        popUpTo(Routes.STUDENT_REGISTER) { inclusive = true }
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
