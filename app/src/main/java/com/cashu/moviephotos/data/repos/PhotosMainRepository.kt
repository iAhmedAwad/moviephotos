/*
 * Copyright (c) 2021.
 * Created by Ahmed Awad
 * ahmed.mmawad@hotmail.com
 */

package com.cashu.moviephotos.data.repos

import android.util.Log
import com.cashu.moviephotos.application.BaseApplication
import com.cashu.moviephotos.data.model.Photo
import com.cashu.moviephotos.data.remote.constants.APIQueries
import com.cashu.moviephotos.data.repos.local.PhotosLocalRepository
import com.cashu.moviephotos.data.repos.remote.PhotosRemoteRepository
import com.cashu.moviephotos.utils.ConnectivityUtils

class PhotosMainRepository {


    private var photosRemoteRepository: PhotosRemoteRepository = PhotosRemoteRepository()
    private var photosLocalRepository: PhotosLocalRepository = PhotosLocalRepository()

    /**
     * Checks for the internet connection and talks to a specific repository accordingly
     * if(isNetworkConnected), gets data from the API
     * else, it gets data from the local repository
     */
    suspend fun getDataAsync(page: Int): Any? {

        return if (ConnectivityUtils(BaseApplication.appContext).isNetworkConnected) {
            photosRemoteRepository.getDataAsync(
                APIQueries.METHOD_PHOTOS_SEARCH, APIQueries.FORMAT_VALUE, APIQueries.TEXT_VALUE,
                page, APIQueries.MIN_PER_PAGE
            )

        } else {
            photosLocalRepository.getDataAsync()
        }
    }


    /**
     * first, this method clears the photo_tbl, then adds a whole page to the database
     * We are adding only the first page to the Room database
     */
    suspend fun addToLocalDatabase(photoList: List<Photo>) {
        photosLocalRepository.deleteAllPhotos()
        photosLocalRepository.addPhotos(photoList)
    }

}
