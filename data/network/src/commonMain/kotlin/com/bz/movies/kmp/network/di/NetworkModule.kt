package com.bz.movies.kmp.network.di

import com.bz.movies.kmp.network.api.service.MovieService
import com.bz.movies.kmp.network.repository.MovieRepository
import com.bz.movies.kmp.network.repository.MovieRepositoryImpl
import org.koin.core.module.Module
import org.koin.dsl.module

val commonNetworkModule = module {

    factory<MovieService> { MovieService() }
    factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }
}

expect val platformNetworkModule: Module