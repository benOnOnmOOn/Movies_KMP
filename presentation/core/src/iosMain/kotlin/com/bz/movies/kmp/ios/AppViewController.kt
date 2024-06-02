package com.bz.movies.kmp.ios

import androidx.compose.ui.window.ComposeUIViewController
import com.bz.movies.kmp.di.presentationModule
import com.bz.movies.kmp.main.MainScreen
import org.koin.core.context.startKoin

@Suppress("unused","FunctionNaming")
fun AppViewController() = ComposeUIViewController {
    MainScreen()
}
