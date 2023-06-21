package com.bz.network.utils

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer

internal fun MockWebServer.enqueueFromFile(
    fileName: String,
    headers: Map<String, String> = emptyMap()
) {
    @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
    val inputStream = javaClass.classLoader
        .getResourceAsStream("api-response/$fileName")
    val source = inputStream.use { it.bufferedReader().readText() }
    val mockResponse = MockResponse()
    for ((key, value) in headers) {
        mockResponse.addHeader(key, value)
    }
    enqueue(
        mockResponse.setBody(source)
    )
}
