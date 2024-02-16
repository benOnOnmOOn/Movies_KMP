@file:Suppress("MatchingDeclarationName")

package com.bz.movies.kmp.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class DriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(MoviesDB2.Schema, MOVIES_DB_NAME)
    }
}
