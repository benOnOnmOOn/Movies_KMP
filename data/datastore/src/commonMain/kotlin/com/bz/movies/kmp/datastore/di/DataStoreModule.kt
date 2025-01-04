package com.bz.movies.kmp.datastore.di

import org.koin.core.module.LazyModule
import org.koin.core.module.Module

internal const val USER_PREFERENCES = "Settings"

expect val datastoreModule: LazyModule
