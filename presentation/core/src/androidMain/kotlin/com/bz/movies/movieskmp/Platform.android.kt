@file:Suppress("MatchingDeclarationName")

package com.bz.movies.movieskmp

class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()
