package com.bz.movies.kmp.database.di

import com.bz.movies.kmp.database.repository.LocalMovieRepository
import com.bz.movies.kmp.database.repository.LocalMovieRepositoryImpl
import org.koin.dsl.module

internal val repositoryModule =
    module {
        factory<LocalMovieRepository> { LocalMovieRepositoryImpl(get()) }
    }
