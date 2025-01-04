package com.bz.movies.kmp.network.di

import android.net.ConnectivityManager
import androidx.core.content.getSystemService
import com.bz.movies.kmp.network.utils.InternetConnection
import com.bz.movies.kmp.network.utils.InternetConnectionImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule

actual val platformNetworkModule: LazyModule =
    lazyModule {
        factory<ConnectivityManager?> { androidApplication().getSystemService<ConnectivityManager>() }
        factory<InternetConnection> { InternetConnectionImpl(get()) }
    }
