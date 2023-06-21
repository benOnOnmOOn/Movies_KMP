package com.bz.movies.core

import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import timber.log.Timber

class CrashlyticsLogTree(
    private val firebaseCrashlytics: FirebaseCrashlytics
) : Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority >= Log.WARN) {
            firebaseCrashlytics.log("$priority/$tag: $message")
        }
        if (t != null) {
            firebaseCrashlytics.recordException(t)
        }
    }

}
