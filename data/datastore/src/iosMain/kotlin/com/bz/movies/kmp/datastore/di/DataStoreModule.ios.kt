package com.bz.movies.kmp.datastore.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import co.touchlab.kermit.Logger
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path.Companion.toPath
import org.koin.dsl.lazyModule
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual val datastoreModule =
    lazyModule {
        single<DataStore<Preferences>> {
            val documentDirectory: NSURL? =
                NSFileManager.defaultManager.URLForDirectory(
                    directory = NSDocumentDirectory,
                    inDomain = NSUserDomainMask,
                    appropriateForURL = null,
                    create = false,
                    error = null,
                )
            val path = requireNotNull(documentDirectory).path + "/$USER_PREFERENCES"

            PreferenceDataStoreFactory.createWithPath(
                corruptionHandler =
                    ReplaceFileCorruptionHandler(
                        produceNewData = {
                            Logger.e("Data store migration error", it)
                            emptyPreferences()
                        },
                    ),
                produceFile = { path.toPath() },
            )
        }
    }
