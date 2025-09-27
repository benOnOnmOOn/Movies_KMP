package com.bz.movies.kmp.network.repository.model

public class MoveDetailDto(
    public val id: Int,
    public val posterUrl: String,
    public val publicationDate: String,
    public val language: String,
    public val title: String,
    public val genre: Set<String>,
    public val overview: String,
)
