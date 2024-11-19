package com.bz.movies.kmp.datastore.di

import com.bz.movies.kmp.datastore.repository.DataStoreRepository
import com.bz.movies.kmp.datastore.repository.DataStoreRepositoryImpl
import org.koin.dsl.module

val datastoreRepositoryModule =
    module {
        single<DataStoreRepository> { DataStoreRepositoryImpl(get()) }
    }
