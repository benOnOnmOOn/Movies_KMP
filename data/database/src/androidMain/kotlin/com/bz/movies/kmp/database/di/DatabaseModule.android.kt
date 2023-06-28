package com.bz.movies.kmp.database.di

import com.bz.movies.kmp.database.DriverFactory
import com.bz.movies.kmp.database.MoviesDB2
import com.bz.movies.kmp.database.createDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val databaseModule = module {
    single<MoviesDB2> { createDatabase(DriverFactory(androidApplication())) }
}