package com.bz.movies.kmp.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val POPULAR_MOVIE_ENTITY_NAME = "POPULAR_MOVIE_ENTITY"

@Entity(tableName = POPULAR_MOVIE_ENTITY_NAME)
internal data class PopularMovieEntity(
    @PrimaryKey @ColumnInfo(name = "ID") val id: Long,
    val posterUrl: String,
    val title: String,
    val publicationDate: String,
    val language: String,
    val rating: Int,
)
