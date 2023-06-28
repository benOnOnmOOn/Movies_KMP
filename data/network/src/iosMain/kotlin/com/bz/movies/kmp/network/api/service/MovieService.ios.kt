package com.bz.movies.kmp.network.api.service

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin

actual fun httpClient(
    config: HttpClientConfig<*>.() -> Unit
): HttpClient = HttpClient(Darwin) {
    config(this)

    engine {
        configureRequest {
            setAllowsCellularAccess(true)
        }
    }
}
