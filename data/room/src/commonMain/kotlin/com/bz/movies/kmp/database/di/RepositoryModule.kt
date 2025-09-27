package com.bz.movies.kmp.database.di

import com.bz.movies.kmp.database.repository.LocalMovieRepository
import com.bz.movies.kmp.database.repository.LocalMovieRepositoryImpl
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule

public val repositoryModule: LazyModule =
    lazyModule {
        factory<LocalMovieRepository> { LocalMovieRepositoryImpl(get(), get(), get()) }
    }
