package com.wassha.schoolmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.wassha.schoolmanagement.data.local.DatabaseSeeder
import com.wassha.schoolmanagement.data.local.database.SchoolDatabase
import com.wassha.schoolmanagement.ui1.SchoolRoot

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize database seeder
        val database = SchoolDatabase.getDatabase(this)
        val seeder = DatabaseSeeder(database, this)

        // Seed database if not already done
        seeder.seedDatabaseIfNeeded()

        setContent {
            SchoolRoot()
        }
    }
}