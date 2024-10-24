package com.bz.movies.kmp.network.utils

internal interface InternetConnection {
    val isConnected: Boolean
        get() = true
}
