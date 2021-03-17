package com.cashu.moviephotos.data.local

import com.cashu.moviephotos.data.model.Photo



class PhotosRepository(private val photosDao: PhotosDao) {

    suspend fun addPhoto(photo: Photo) {
        photosDao.addPhoto(photo)
    }

    suspend fun deleteAllPhotos() {
        photosDao.deleteAllPhotos()
    }


}
