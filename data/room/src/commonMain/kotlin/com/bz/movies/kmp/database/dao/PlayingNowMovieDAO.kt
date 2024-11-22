package com.bz.movies.kmp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bz.movies.kmp.database.entity.PlayingNowMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PlayingNowMovieDAO : BaseDao<PlayingNowMovieEntity> {
    @Query("SELECT * FROM ${PlayingNowMovieEntity.ENTITY_NAME}")
    fun observeAllMovies(): Flow<List<PlayingNowMovieEntity>>

    @Query("DELETE FROM ${PlayingNowMovieEntity.ENTITY_NAME}")
    suspend fun clearTable()
}
