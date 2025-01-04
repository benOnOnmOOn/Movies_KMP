package com.bz.movies.kmp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bz.movies.kmp.database.entity.MOVIE_ENTITY_NAME
import com.bz.movies.kmp.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MovieDAO : BaseDao<MovieEntity> {
    @Query("SELECT * FROM $MOVIE_ENTITY_NAME")
    fun observeAllMovies(): Flow<List<MovieEntity>>
}
