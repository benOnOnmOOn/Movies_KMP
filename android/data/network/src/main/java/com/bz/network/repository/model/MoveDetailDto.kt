package com.bz.network.repository.model

data class MoveDetailDto(
    val id: Int,
    val posterUrl: String,
    val publicationDate: String,
    val language: String,
    val title: String,
    val genre: Set<String>,
    val overview: String,
)
