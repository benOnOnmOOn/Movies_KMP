package com.bz.movies.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

internal interface BaseDao<T> {
    /**
     * Insert an object in the database.
     *
     * @param obj the object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: T)

    /**
     * Insert an array of objects in the database.
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)

    /**
     * Insert an list of objects in the database.
     * @param obj the objects to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(obj: List<T>)

    /**
     * Update an object from the database.
     *
     * @param obj the object to be updated
     */
    @Update
    suspend fun update(obj: T)

    /**
     * Update an array of objects from the database.
     *
     * @param obj the objects to be updated
     */
    @Update
    suspend fun update(vararg obj: T)

    /**
     * Update an array of objects from the database.
     *
     * @param obj the objects to be updated
     */
    @Update
    suspend fun update(obj: List<T>)

    /**
     * Delete an object from the database
     *
     * @param obj the object to be deleted
     */
    @Delete
    suspend fun delete(obj: T)
}
