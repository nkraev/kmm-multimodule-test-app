package com.example.wbdtestapp.database

import com.squareup.sqldelight.db.SqlDriver

expect abstract class DatabaseDependencies {
    val driver: SqlDriver
    val database: AppDatabase
    val databaseQueries: AppDatabaseQueries
}