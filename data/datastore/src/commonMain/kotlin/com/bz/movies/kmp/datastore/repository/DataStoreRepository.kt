package com.bz.movies.kmp.datastore.repository

import kotlinx.datetime.Instant

interface DataStoreRepository {
    suspend fun insertPlayingNowRefreshDate(data: Instant)

    suspend fun getPlyingNowRefreshDate(): Instant

    suspend fun insertPopularNowRefreshDate(data: Instant)

    suspend fun getPopularRefreshDate(): Instant
}
