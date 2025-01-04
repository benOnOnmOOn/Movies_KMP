package com.bz.movies.kmp.database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bz.movies.kmp.database.DATABASE_NAME
import com.bz.movies.kmp.database.MoviesDatabase
import com.bz.movies.kmp.database.dao.MovieDAO
import com.bz.movies.kmp.database.dao.PlayingNowMovieDAO
import com.bz.movies.kmp.database.dao.PopularMovieDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.LazyModule
import org.koin.dsl.lazyModule

private fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<MoviesDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath(DATABASE_NAME)
    return Room.databaseBuilder<MoviesDatabase>(
        context = appContext,
        name = dbFile.absolutePath,
    )
}

actual val databaseModule: LazyModule =
    lazyModule {
        single<MoviesDatabase> { getDatabaseBuilder(androidApplication()).build() }
        single<MovieDAO> { get<MoviesDatabase>().movieDAO() }
        single<PlayingNowMovieDAO> { get<MoviesDatabase>().playingNowMovieDAO() }
        single<PopularMovieDAO> { get<MoviesDatabase>().popularMovieDAO() }
    }
