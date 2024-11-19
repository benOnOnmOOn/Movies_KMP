package com.bz.movies.kmp.datastore.repository

import kotlinx.datetime.Instant

interface DataStoreRepository {
    suspend fun insertPlayingNowRefreshDate(data: Instant): Result<Unit>

    suspend fun getPlyingNowRefreshDate(): Result<Instant>

    suspend fun insertPopularNowRefreshDate(data: Instant): Result<Unit>

    suspend fun getPopularRefreshDate(): Result<Instant>
}
