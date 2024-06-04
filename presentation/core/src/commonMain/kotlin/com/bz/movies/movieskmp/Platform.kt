package com.bz.movies.movieskmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
