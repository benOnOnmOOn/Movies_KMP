package com.bz.movies.database.di

import com.bz.movies.database.MoviesDatabase
import com.bz.movies.database.createMoviesDatabase
import com.bz.movies.database.dao.MovieDAO
import com.bz.movies.database.dao.PlayingNowMovieDAO
import com.bz.movies.database.dao.PopularMovieDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single<MoviesDatabase> { createMoviesDatabase(androidApplication()) }
    single<MovieDAO> { get<MoviesDatabase>().movieDAO() }
    single<PlayingNowMovieDAO> { get<MoviesDatabase>().playingNowMovieDAO() }
    single<PopularMovieDAO> { get<MoviesDatabase>().popularMovieDAO() }
}
