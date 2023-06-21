package com.bz.movies.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = MovieEntity.ENTITY_NAME)
internal data class MovieEntity(
    @PrimaryKey @ColumnInfo(name = COLUMN_ID) val id: Long,
    val posterUrl: String,
    val title: String,
    val publicationDate: String,
    val language: String,
    val rating: Int,
) {
    companion object {
        internal const val ENTITY_NAME = "MOVIE_ENTITY"
        internal const val COLUMN_ID = "ID"
    }
}
