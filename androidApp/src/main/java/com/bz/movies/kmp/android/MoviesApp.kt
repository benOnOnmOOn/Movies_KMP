package com.bz.movies.kmp.android

import android.app.Application
import android.os.StrictMode
import co.touchlab.kermit.Logger
import com.bz.movies.kmp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import java.util.concurrent.Executors

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(presentationModule)
        }

        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectAll()
                .penaltyListener(Executors.newSingleThreadScheduledExecutor()) {
                    Logger.w(it) { "Thread policy error ${it.message}" }
                }
                .build(),
        )

        StrictMode.setVmPolicy(
            StrictMode.VmPolicy.Builder()
                .detectAll()
                .penaltyListener(Executors.newSingleThreadScheduledExecutor()) {
                    Logger.w(it) { "VM policy error ${it.message}" }
                }
                .build(),
        )
    }
}
