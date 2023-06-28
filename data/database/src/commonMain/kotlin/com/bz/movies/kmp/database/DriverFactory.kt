package com.bz.movies.kmp.database

import app.cash.sqldelight.db.SqlDriver

const val MOVIES_DB_NAME = "Movies2DB"
expect class DriverFactory {
    fun createDriver(): SqlDriver

}

fun createDatabase(driverFactory: DriverFactory): MoviesDB2 {
    val driver = driverFactory.createDriver()
    return MoviesDB2(driver)

}