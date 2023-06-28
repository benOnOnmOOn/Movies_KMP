package com.bz.movies.database.di

import com.bz.movies.database.repository.LocalMovieRepository
import com.bz.movies.database.repository.LocalMovieRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    factory<LocalMovieRepository> { LocalMovieRepositoryImpl(get(), get(), get()) }
}
