package com.bz.movies.kmp.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class DriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
       return AndroidSqliteDriver(MoviesDB2.Schema, context, MOVIES_DB_NAME)
    }
}