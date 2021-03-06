/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cashu.moviephotos.data.model.Photo
import kotlinx.coroutines.Deferred

@Dao
 interface PhotosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPhoto(photo: Photo)

    @Query("Delete FROM photos_tbl")
    suspend fun deleteAllPhotos()

    @Query("SELECT * FROM photos_tbl")
    suspend fun getDataAsync(): List<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPhotos(list: List<Photo>)
}
