package com.bz.movies.kmp.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStoreFile
import co.touchlab.kermit.Logger
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule

actual val datastoreModule: LazyModule =
    lazyModule {
        single<DataStore<Preferences>> {
            PreferenceDataStoreFactory.create(
                corruptionHandler =
                    ReplaceFileCorruptionHandler(
                        produceNewData = {
                            Logger.e("Data store migration error", it)
                            emptyPreferences()
                        },
                    ),
                migrations = listOf(SharedPreferencesMigration(androidApplication(), USER_PREFERENCES)),
                produceFile = { androidApplication().preferencesDataStoreFile(USER_PREFERENCES) },
            )
        }
    }
