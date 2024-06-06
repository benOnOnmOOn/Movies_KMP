package com.bz.movies.core

import android.content.Context
import androidx.startup.Initializer
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter

class AppInitializer : Initializer<Unit> {
    @OptIn(ExperimentalKermitApi::class)
    override fun create(context: Context) {
        Logger.setLogWriters(listOf(CrashlyticsLogWriter()))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
