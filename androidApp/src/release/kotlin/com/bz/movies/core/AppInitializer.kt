package com.bz.movies.core

import android.content.Context
import androidx.startup.Initializer
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class AppInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Timber.plant(CrashlyticsLogTree(FirebaseCrashlytics.getInstance()))
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
