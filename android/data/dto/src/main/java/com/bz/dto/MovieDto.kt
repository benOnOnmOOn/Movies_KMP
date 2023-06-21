package com.bz.dto

data class MovieDto(
    val id: Int,
    val posterUrl: String,
    val title: String,
    val publicationDate: String,
    val language: String,
    val rating: Int,
)
