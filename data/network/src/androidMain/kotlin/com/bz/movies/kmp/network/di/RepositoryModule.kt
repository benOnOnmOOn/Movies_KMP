package com.bz.movies.kmp.network.di

import com.bz.movies.kmp.network.api.service.MovieService
import com.bz.movies.kmp.network.repository.MovieRepository
import com.bz.movies.kmp.network.repository.MovieRepositoryImpl
import com.bz.movies.kmp.network.utils.InternetConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal class RepositoryModule {

    @Provides
    internal fun provideMovieRepository(
        internetConnection: InternetConnection,
    ): MovieRepository = MovieRepositoryImpl(MovieService(), internetConnection)
}
