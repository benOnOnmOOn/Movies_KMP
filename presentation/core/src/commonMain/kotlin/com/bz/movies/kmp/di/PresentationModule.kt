package com.bz.movies.kmp.di

import com.bz.movies.presentation.di.screensModule
import org.koin.core.module.Module

public val presentationModule: List<Lazy<Module>> = screensModule
