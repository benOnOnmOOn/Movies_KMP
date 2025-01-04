package com.bz.movies.kmp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bz.movies.kmp.database.entity.POPULAR_MOVIE_ENTITY_NAME
import com.bz.movies.kmp.database.entity.PopularMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PopularMovieDAO : BaseDao<PopularMovieEntity> {
    @Query("SELECT * FROM $POPULAR_MOVIE_ENTITY_NAME")
    fun observeAllMovies(): Flow<List<PopularMovieEntity>>

    @Query("DELETE FROM $POPULAR_MOVIE_ENTITY_NAME")
    suspend fun clearTable()
}
