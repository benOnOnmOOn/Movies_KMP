package com.bz.movies.kmp.datastore.di

import org.koin.core.module.LazyModule

internal const val USER_PREFERENCES = "Settings"

public expect val datastoreModule: LazyModule
