/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.repos.local

import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.data.local.PhotosDao
import com.cashu.moviephotos.data.local.db.PhotosDatabase
import com.cashu.moviephotos.data.model.Photo


class PhotosLocalRepository() {

    private val photosDao: PhotosDao =
        PhotosDatabase.getDatabase(BaseApplication.appContext)
            .photosDao()

    suspend fun addPhoto(photo: Photo) {
        photosDao.addPhoto(photo)
    }

    suspend fun addPhotos(list: List<Photo>) {
        photosDao.addPhotos(list)
    }

    suspend fun deleteAllPhotos() {
        photosDao.deleteAllPhotos()
    }

    suspend fun getDataAsync(): List<Photo> {
        return photosDao.getDataAsync()
    }

}
