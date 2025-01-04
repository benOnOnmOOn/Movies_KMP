package com.bz.movies.kmp.android

import android.os.Build
import android.os.StrictMode
import java.util.concurrent.Executors
import co.touchlab.kermit.Logger

fun enableStrictMode(){
    StrictMode.setThreadPolicy(
        StrictMode.ThreadPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    penaltyListener(Executors.newSingleThreadScheduledExecutor()) {
                        Logger.w(it) { "Thread policy error ${it.message}" }
                    }
                } else {
                    penaltyLog()
                }
            }
            .build(),
    )

    StrictMode.setVmPolicy(
        StrictMode.VmPolicy.Builder()
            .detectAll()
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    penaltyListener(Executors.newSingleThreadScheduledExecutor()) {
                        Logger.w(it) { "VM policy error ${it.message}" }
                    }
                } else {
                    penaltyLog()
                }
            }
            .build(),
    )
}