package com.bz.movies.kmp.network.di

import com.bz.movies.kmp.network.utils.InternetConnection
import com.bz.movies.kmp.network.utils.InternetConnectionImpl
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule

actual val platformNetworkModule: LazyModule =
    lazyModule {
        factory<InternetConnection> { InternetConnectionImpl() }
    }
