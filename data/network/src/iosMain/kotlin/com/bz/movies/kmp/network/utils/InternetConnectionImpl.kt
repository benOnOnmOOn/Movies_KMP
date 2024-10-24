package com.bz.movies.kmp.network.utils

internal actual class InternetConnectionImpl : InternetConnection {
    override val isConnected: Boolean
        get() = true
}
