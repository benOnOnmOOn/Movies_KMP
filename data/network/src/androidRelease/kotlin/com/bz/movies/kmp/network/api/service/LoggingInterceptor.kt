package com.bz.movies.kmp.network.api.service

import okhttp3.OkHttpClient

@Suppress("NOTHING_TO_INLINE")
inline fun OkHttpClient.Builder.configureLoggingInterceptor(){
    // no-op as we don't have any specific interceptor for release
}