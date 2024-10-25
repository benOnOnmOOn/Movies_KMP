package com.bz.movies.kmp.database

import app.cash.sqldelight.db.SqlDriver

internal const val MOVIES_DB_NAME = "Movies2DB"

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class DriverFactory {
    fun createDriver(): SqlDriver
}

internal fun createDatabase(driverFactory: DriverFactory): MoviesDB2 {
    val driver = driverFactory.createDriver()
    return MoviesDB2(driver)
}
