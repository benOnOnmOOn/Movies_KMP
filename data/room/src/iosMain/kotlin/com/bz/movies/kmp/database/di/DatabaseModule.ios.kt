package com.bz.movies.kmp.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.bz.movies.kmp.database.DATABASE_NAME
import com.bz.movies.kmp.database.MoviesDatabase
import com.bz.movies.kmp.database.dao.MovieDAO
import com.bz.movies.kmp.database.dao.PlayingNowMovieDAO
import com.bz.movies.kmp.database.dao.PopularMovieDAO
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

private fun getDatabaseBuilder(): RoomDatabase.Builder<MoviesDatabase> {
    val dbFilePath = "${documentDirectory()}/$DATABASE_NAME"
    return Room.databaseBuilder<MoviesDatabase>(
        name = dbFilePath,
    )
}

@OptIn(ExperimentalForeignApi::class)
private fun documentDirectory(): String {
    val documentDirectory =
        NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )
    return requireNotNull(documentDirectory?.path)
}

actual val databaseModule: LazyModule =
    lazyModule {
        single<MoviesDatabase> { getDatabaseBuilder().build() }
        single<MovieDAO> { get<MoviesDatabase>().movieDAO() }
        single<PlayingNowMovieDAO> { get<MoviesDatabase>().playingNowMovieDAO() }
        single<PopularMovieDAO> { get<MoviesDatabase>().popularMovieDAO() }
    }
