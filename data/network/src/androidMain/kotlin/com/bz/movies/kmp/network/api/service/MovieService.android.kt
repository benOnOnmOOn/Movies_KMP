package com.bz.movies.kmp.network.api.service

import android.net.TrafficStats
import com.bz.movies.kmp.network.utils.DelegatingSocketFactory
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import javax.net.SocketFactory

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit): HttpClient =
    HttpClient(OkHttp) {
        config(this)
        engine {
            config {
                socketFactory(
                    DelegatingSocketFactory(SocketFactory.getDefault()) { socket ->
                        TrafficStats.setThreadStatsTag(1)
                        socket
                    },
                )
                retryOnConnectionFailure(false)
                configureLoggingInterceptor()
            }
        }
    }
