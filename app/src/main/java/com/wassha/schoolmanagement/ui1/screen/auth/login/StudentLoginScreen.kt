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
fun StudentLoginScreen(
    navController: NavController
) {
    // ✅ Get Application safely
    val application = LocalContext.current.applicationContext as Application

    // ✅ Create ViewModel with Factory (Room depends on this)
    val authViewModel: AuthViewModel = viewModel(
        factory = AuthViewModelFactory.getInstance(application)
    )

    // ✅ REQUIRED login fields (as per new rule)
    var className by remember { mutableStateOf("") }
    var section by remember { mutableStateOf("") }
    var rollNumber by remember { mutableStateOf("") }
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
            text = "Student Login",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ CLASS
        OutlinedTextField(
            value = className,
            onValueChange = { className = it },
            label = { Text("Class") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ✅ SECTION
        OutlinedTextField(
            value = section,
            onValueChange = { section = it },
            label = { Text("Section") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ✅ ROLL NUMBER
        OutlinedTextField(
            value = rollNumber,
            onValueChange = { rollNumber = it },
            label = { Text("Roll Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ✅ PASSWORD
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ LOGIN BUTTON (FIXED PARAMS)
        Button(
            onClick = {
                authViewModel.loginStudent(
                    className = className,
                    section = section,
                    rollNumber = rollNumber,
                    password = password
                )
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ REGISTER REDIRECT (must exist in Routes)
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Don't have an account? ")
            TextButton(
                onClick = {
                    navController.navigate(Routes.STUDENT_REGISTER)
                }
            ) {
                Text("Register")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ✅ UI STATE HANDLING
        when (authState) {

            is AuthUiState.Loading -> {
                CircularProgressIndicator()
            }

            is AuthUiState.StudentSuccess -> {
                val student = (authState as AuthUiState.StudentSuccess).student

                LaunchedEffect(student) {
                    navController.currentBackStackEntry
                        ?.savedStateHandle
                        ?.set("student", student)

                    navController.navigate(Routes.STUDENT_DASHBOARD)
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
