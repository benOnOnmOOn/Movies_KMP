package com.bz.movies.kmp.network.di

import com.bz.movies.kmp.network.api.service.MovieService
import com.bz.movies.kmp.network.repository.MovieRepository
import com.bz.movies.kmp.network.repository.MovieRepositoryImpl
import org.koin.core.module.LazyModule
import org.koin.core.module.Module
import org.koin.dsl.lazyModule
import org.koin.dsl.module

val commonNetworkModule: LazyModule =
    lazyModule {
        factory<MovieService> { MovieService() }
        factory<MovieRepository> { MovieRepositoryImpl(get(), get()) }
    }

expect val platformNetworkModule: LazyModule
