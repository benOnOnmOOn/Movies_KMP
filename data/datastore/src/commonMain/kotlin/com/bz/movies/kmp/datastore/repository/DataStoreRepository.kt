package com.bz.movies.kmp.datastore.repository

import kotlin.time.Instant

public interface DataStoreRepository {
    public suspend fun insertPlayingNowRefreshDate(data: Instant): Result<Unit>

    public suspend fun getPlyingNowRefreshDate(): Result<Instant>

    public suspend fun insertPopularNowRefreshDate(data: Instant): Result<Unit>

    public suspend fun getPopularRefreshDate(): Result<Instant>
}
