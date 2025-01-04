package com.bz.movies.kmp.android

import android.app.Application
import android.os.Build
import android.os.StrictMode
import co.touchlab.kermit.Logger
import com.bz.movies.kmp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.lazyModules
import java.util.concurrent.Executors

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            lazyModules(presentationModule)
        }

        enableStrictMode()
    }
}
