package com.bz.movies.kmp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bz.movies.kmp.database.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface MovieDAO : BaseDao<MovieEntity> {
    @Query("SELECT * FROM ${MovieEntity.ENTITY_NAME}")
    fun observeAllMovies(): Flow<List<MovieEntity>>
}
