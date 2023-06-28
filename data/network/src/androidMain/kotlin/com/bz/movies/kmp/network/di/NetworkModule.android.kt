package com.bz.movies.kmp.network.di

import android.content.Context
import android.net.ConnectivityManager
import com.bz.movies.kmp.network.utils.InternetConnection
import com.bz.movies.kmp.network.utils.InternetConnectionImpl
import androidx.core.content.getSystemService
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

actual val platformNetworkModule = module {

    factory<ConnectivityManager?> { androidApplication().getSystemService<ConnectivityManager>() }
    factory<InternetConnection> { InternetConnectionImpl(get()) }
}
