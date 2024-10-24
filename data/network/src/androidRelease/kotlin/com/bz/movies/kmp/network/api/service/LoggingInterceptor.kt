package com.bz.movies.kmp.network.api.service

import io.ktor.client.HttpClientConfig

@Suppress("NOTHING_TO_INLINE")
internal inline fun HttpClientConfig<*>.configureLoggingInterceptor() {
    // no-op as we don't have any specific interceptor for release
}
