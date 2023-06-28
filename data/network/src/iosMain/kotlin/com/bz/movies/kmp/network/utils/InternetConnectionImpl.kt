package com.bz.movies.kmp.network.utils

actual class InternetConnectionImpl : InternetConnection {
    override val isConnected: Boolean
        get() = true
}
