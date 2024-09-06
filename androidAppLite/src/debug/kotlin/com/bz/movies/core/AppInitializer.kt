package com.bz.movies.core

import android.content.Context
import androidx.startup.Initializer
import co.touchlab.kermit.LogcatWriter
import co.touchlab.kermit.Logger

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Logger.setLogWriters(listOf(LogcatWriter()))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
