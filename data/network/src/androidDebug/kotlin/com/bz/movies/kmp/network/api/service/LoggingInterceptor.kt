package com.bz.movies.kmp.network.api.service

import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.logging.Logging

@Suppress("NOTHING_TO_INLINE")
inline fun HttpClientConfig<*>.configureLoggingInterceptor() {
    install(Logging)
}
