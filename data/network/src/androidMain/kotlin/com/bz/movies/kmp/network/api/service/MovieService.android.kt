package com.bz.movies.kmp.network.api.service

import android.net.TrafficStats
import com.bz.movies.kmp.network.utils.DelegatingSocketFactory
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.android.Android
import javax.net.SocketFactory

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient = HttpClient(Android) {
    config(this)

    configureLoggingInterceptor()

}
