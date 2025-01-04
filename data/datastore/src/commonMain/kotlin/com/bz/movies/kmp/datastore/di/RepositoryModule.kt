package com.bz.movies.kmp.datastore.di

import com.bz.movies.kmp.datastore.repository.DataStoreRepository
import com.bz.movies.kmp.datastore.repository.DataStoreRepositoryImpl
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule
import org.koin.dsl.module

val datastoreRepositoryModule: LazyModule =
    lazyModule {
        single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    }
