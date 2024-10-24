package com.bz.movies.kmp.di

import com.bz.movies.kmp.database.di.databaseModule
import com.bz.movies.kmp.database.di.repositoryModule
import com.bz.movies.kmp.network.di.commonNetworkModule
import com.bz.movies.kmp.network.di.platformNetworkModule
import com.bz.movies.presentation.di.screensModule

val presentationModule =
    screensModule + commonNetworkModule + platformNetworkModule + databaseModule + repositoryModule
