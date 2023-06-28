package com.bz.movies.kmp.database.di

import com.bz.movies.kmp.database.MoviesDB2
import com.bz.movies.kmp.database.MoviesDB2Queries
import org.koin.core.module.Module
import org.koin.dsl.module

expect val databaseModule: Module

val queriesModule = module {
    single<MoviesDB2Queries> { get<MoviesDB2>().moviesDB2Queries }
}
