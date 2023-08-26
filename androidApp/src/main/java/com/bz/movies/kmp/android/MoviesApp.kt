package com.bz.movies.kmp.android

import android.app.Application
import com.bz.movies.kmp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class MoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@MoviesApp)
            modules(presentationModule)
        }
    }
}
