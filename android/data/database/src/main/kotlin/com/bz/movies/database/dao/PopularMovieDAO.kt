package com.bz.movies.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bz.movies.database.entity.PopularMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PopularMovieDAO : BaseDao<PopularMovieEntity> {

    @Query("SELECT * FROM ${PopularMovieEntity.ENTITY_NAME}")
    fun observeAllMovies(): Flow<List<PopularMovieEntity>>

    @Query("DELETE FROM ${PopularMovieEntity.ENTITY_NAME}")
    fun clearTable()

}
