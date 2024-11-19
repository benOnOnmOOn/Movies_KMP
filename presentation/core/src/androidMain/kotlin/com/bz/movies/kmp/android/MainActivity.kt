@file:Suppress("FunctionNaming")

package com.bz.movies.kmp.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.bz.movies.kmp.main.MainScreen
import org.koin.compose.KoinContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {  KoinContext() { MainScreen() }}
    }
}
