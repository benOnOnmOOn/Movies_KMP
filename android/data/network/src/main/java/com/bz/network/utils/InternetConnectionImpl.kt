package com.bz.network.utils

import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class InternetConnectionImpl(
    private val connectivityManager: ConnectivityManager?,
) : InternetConnection {

    override val isConnected: Boolean
        get() = connectivityManager
            ?.getNetworkCapabilities(connectivityManager.activeNetwork)
            ?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
}
