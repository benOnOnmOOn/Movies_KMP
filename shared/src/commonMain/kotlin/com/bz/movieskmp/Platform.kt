package com.bz.movieskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
