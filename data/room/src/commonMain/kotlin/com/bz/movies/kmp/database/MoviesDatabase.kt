package com.bz.movies.kmp.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.bz.movies.kmp.database.dao.MovieDAO
import com.bz.movies.kmp.database.dao.PlayingNowMovieDAO
import com.bz.movies.kmp.database.dao.PopularMovieDAO
import com.bz.movies.kmp.database.entity.MovieEntity
import com.bz.movies.kmp.database.entity.PlayingNowMovieEntity
import com.bz.movies.kmp.database.entity.PopularMovieEntity

internal const val DATABASE_NAME = "MoviesDB"

@Database(
    entities = [MovieEntity::class, PlayingNowMovieEntity::class, PopularMovieEntity::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2),
    ],
)
internal abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDAO(): MovieDAO

    abstract fun playingNowMovieDAO(): PlayingNowMovieDAO

    abstract fun popularMovieDAO(): PopularMovieDAO
}
