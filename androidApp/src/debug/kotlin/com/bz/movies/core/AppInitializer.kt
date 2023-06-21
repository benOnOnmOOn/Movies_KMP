package com.bz.movies.core

import android.content.Context
import androidx.startup.Initializer
import timber.log.Timber

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.plant(Timber.DebugTree())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
