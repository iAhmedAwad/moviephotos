package com.cashu.moviephotos.data.repos.local

import androidx.lifecycle.LiveData
import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.data.local.PhotosDao
import com.cashu.moviephotos.data.local.db.PhotosDatabase
import com.cashu.moviephotos.data.model.Photo
import kotlinx.coroutines.Deferred


class PhotosLocalRepository() {

    private val photosDao: PhotosDao =
        PhotosDatabase.getDatabase(BaseApplication.appContext)
            .photosDao()

    suspend fun addPhoto(photo: Photo) {
        photosDao.addPhoto(photo)
    }

    suspend fun deleteAllPhotos() {
        photosDao.deleteAllPhotos()
    }

    suspend fun getDataAsync(): List<Photo> {
        return photosDao.getDataAsync()
    }

}
