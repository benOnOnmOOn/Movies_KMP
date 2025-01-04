package com.bz.movies.kmp.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.bz.movies.kmp.database.entity.PLAYING_NOW_ENTITY_NAME
import com.bz.movies.kmp.database.entity.PlayingNowMovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PlayingNowMovieDAO : BaseDao<PlayingNowMovieEntity> {
    @Query("SELECT * FROM $PLAYING_NOW_ENTITY_NAME")
    fun observeAllMovies(): Flow<List<PlayingNowMovieEntity>>

    @Query("DELETE FROM $PLAYING_NOW_ENTITY_NAME")
    suspend fun clearTable()
}
