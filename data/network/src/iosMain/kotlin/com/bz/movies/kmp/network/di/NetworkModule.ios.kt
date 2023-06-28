package com.bz.movies.kmp.network.di

import com.bz.movies.kmp.network.utils.InternetConnection
import com.bz.movies.kmp.network.utils.InternetConnectionImpl
import org.koin.dsl.module

actual val platformNetworkModule = module {
    factory<InternetConnection> { InternetConnectionImpl() }
}