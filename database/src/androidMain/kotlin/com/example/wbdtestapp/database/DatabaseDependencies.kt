package com.example.wbdtestapp.database

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual abstract class DatabaseDependencies {
    abstract val context: Context
    actual val driver: SqlDriver by lazy { AndroidSqliteDriver(AppDatabase.Schema, context, "test.db") }
    actual val database: AppDatabase by lazy { AppDatabase(driver) }
    actual val databaseQueries: AppDatabaseQueries by lazy { database.appDatabaseQueries }
}