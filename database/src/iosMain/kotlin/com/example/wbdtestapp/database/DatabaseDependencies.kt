package com.example.wbdtestapp.database

import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual abstract class DatabaseDependencies {
    actual val driver: SqlDriver = NativeSqliteDriver(AppDatabase.Schema, "test.db")
    actual val database: AppDatabase = AppDatabase(driver)
    actual val databaseQueries: AppDatabaseQueries = database.appDatabaseQueries
}