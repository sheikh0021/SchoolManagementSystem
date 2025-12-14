package com.wassha.schoolmanagement.ui1

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.wassha.schoolmanagement.ui.theme.SchoolManagementTheme
import com.wassha.schoolmanagement.ui1.navigation.AppNavGraph


@Composable
fun SchoolRoot(){
    val navController = rememberNavController()

    SchoolManagementTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            AppNavGraph(navController = navController)
        }
    }
}
